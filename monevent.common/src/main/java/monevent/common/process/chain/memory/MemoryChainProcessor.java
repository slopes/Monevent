package monevent.common.process.chain.memory;

import com.eaio.uuid.UUID;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.query.AndQueryOperator;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.chain.ChainProcessorBase;
import monevent.common.process.chain.Chaining;
import monevent.common.process.chain.IChainingChecker;
import monevent.common.process.chain.QueryChainingChecker;
import monevent.common.store.IStore;
import monevent.common.store.StoreException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by slopes on 08/02/17.
 */
public class MemoryChainProcessor extends ChainProcessorBase {

    protected static String chain = "chain";
    protected static String subNodes = "subNodes";
    protected static String superNode = "superNode";

    private final IStore store;
    private final Map<Chaining, IChainingChecker> checkers;

    public MemoryChainProcessor(String name, IQuery query, List<Chaining> chainingList, Manager manager, String resultBus, String store) {
        super(name, query, chainingList, manager, resultBus);
        if (!Strings.isNullOrEmpty(store) && manager != null) {
            this.store = manager.get(store);
        } else {
            this.store = null;
        }
        this.checkers = new ConcurrentHashMap<>();
        chainingList.forEach(c -> this.checkers.put(c, new QueryChainingChecker(c.getCompleteQuery())));
    }

    @Override
    protected boolean doCheck(Chaining chaining, IEntity entity) throws StoreException {
        debug("Checking entity %s %s", entity.getType(), entity.getId());
        if (entity == null) return false;
        IChainingChecker checker = this.checkers.get(chaining);
        if (checker == null) return false;

        if (checker.isComplete(entity)) {
            if (entity.getValueAsString(MemoryChainProcessor.chain) != null) {
                this.store.delete(entity.getId());
                debug("Removing entity %s %s", entity.getType(), entity.getId());
                return true;
            }
        } else {
            this.store.update(entity);
        }
        return false;
    }

    @Override
    protected void doChain(Chaining chaining, IEntity entity) throws StoreException {
        debug("Processing entity %s %s", entity.getType(), entity.getId());
        if (entity == null || this.store == null) return;

        this.store.create(entity);

        // Get chain ID is ever
        String chain;
        if (chaining.isRoot()) {
            chain = new UUID().toString();
            entity.setValue(MemoryChainProcessor.chain, chain);
        }

        List<String> subFields = chaining.getSubFields();
        if (subFields != null && subFields.size() > 0) {

            List<String> subNodes = entity.getValueAsList(MemoryChainProcessor.subNodes);

            if (subNodes == null) {
                subNodes = Lists.newArrayList();
                entity.setValue(MemoryChainProcessor.subNodes, subNodes);

            }


            final Query query = new Query();
            subFields.forEach(f -> query.addCriterion(f, entity.getValue(f), QueryCriterionType.Is));

            final IQuery subQuery = new AndQueryOperator(query,chaining.getSubNodeQuery());

            List<IEntity> unKnownSubNodes = this.store.read(subQuery);
            if (unKnownSubNodes != null && unKnownSubNodes.size() > 0) {
                debug("Found %s sub nodes", unKnownSubNodes.size());
                for (IEntity subNode : unKnownSubNodes) {
                    subNode.setValue(MemoryChainProcessor.superNode, entity.getId());
                    subNodes.add(subNode.getId());
                    check(subNode);
                }
            } else {
                debug("Found %s sub nodes", "no");
            }
        }


        if (!chaining.isRoot() && entity.getValue(MemoryChainProcessor.superNode) == null) {
            List<String> superFields = chaining.getSuperFields();
            if (superFields != null && superFields.size() > 0) {
                final Query query = new Query();
                superFields.forEach(f -> query.addCriterion(f, entity.getValue(f), QueryCriterionType.Is));

                final IQuery superQuery = new AndQueryOperator(query,chaining.getSuperNodeQuery());

                List<IEntity> knownSuperNodes = this.store.read(superQuery);
                if (knownSuperNodes != null && knownSuperNodes.size() == 1) {
                    debug("Found %s super nodes", knownSuperNodes.size());
                    final IEntity superNode = knownSuperNodes.get(0);
                    entity.setValue(MemoryChainProcessor.superNode, superNode.getId());
                    List<String> siblings = superNode.getValueAsList(MemoryChainProcessor.subNodes);
                    if (siblings == null) {
                        siblings = Lists.newArrayList();
                        superNode.setValue(MemoryChainProcessor.subNodes, siblings);
                    }
                    siblings.add(entity.getId());
                    chain = superNode.getValueAsString(MemoryChainProcessor.chain);
                    if (!Strings.isNullOrEmpty(chain)) {
                        entity.setValue(MemoryChainProcessor.chain, chain);
                    }
                    check(superNode);
                } else {
                    debug("Found %s super nodes", knownSuperNodes.size());
                    if (knownSuperNodes.size() > 1) {
                        warn("A node cannot have more than 1 super node !! %s : %s", entity.getId(), entity.getType());
                    }
                }
            }
        }


        chain = entity.getValueAsString(MemoryChainProcessor.chain);
        if (!Strings.isNullOrEmpty(chain)) {
            propagateChain(entity, chain);
        }

    }

    private void propagateChain(IEntity entity, String chain) throws StoreException {
        entity.setValue(MemoryChainProcessor.chain, chain);
        check(entity);
        List<String> subNodes = entity.getValueAsList(MemoryChainProcessor.subNodes);
        if (subNodes != null && subNodes.size() > 0) {
            for (String nodeId : subNodes) {
                IEntity subNode = this.store.read(nodeId);
                if (subNode != null) {
                    propagateChain(subNode, chain);
                }
            }
        }
    }


}
