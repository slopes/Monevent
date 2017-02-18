package monevent.common.process.matching.memory;


import com.eaio.uuid.UUID;
import javassist.*;
import monevent.common.model.IEntity;
import monevent.common.process.matching.Matching;

import java.util.List;


/**
 * Created by slopes on 01/02/17.
 */
public class MemoryCacheKeyFactory implements IMemoryCacheKeyFactory {


    private final Class keyClass;
    private final String keyType;
    private final String resultType;
    private final List<String> fields;

    public MemoryCacheKeyFactory(Matching matching) {
        this.keyType = build(matching.getType());
        this.fields = matching.getFields();
        this.resultType = matching.getType();
        this.keyClass = buildClass();
    }

    @Override
    public IMemoryCacheKey buildKey(IEntity entity) {
        if (this.keyClass == null) return null;
        try {
            if (entity != null) {
                for (String field : this.fields) {
                    if (!entity.contains(field)) return null;
                }
            }

            IMemoryCacheKey key = (IMemoryCacheKey) this.keyClass.newInstance();
            if (entity != null) {
                for (String field : this.fields) {
                    key.setValue(field, entity.getValue(field));
                }
            }

            return key;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private Class buildClass() {
        ClassPool pool = ClassPool.getDefault();
        CtClass keyClass = pool.makeClass(this.keyType);

        try {
            for (String field : this.fields) {
                addProperty(field, pool, keyClass);
            }
            keyClass = addBuildMethod(pool, keyClass);
            keyClass = addSetValueMethod(keyClass);
            keyClass = addHashCode(keyClass);
            keyClass = addEquals(keyClass);
            return keyClass.toClass();
        } catch (CannotCompileException e) {
            return null;
        } catch (NotFoundException e) {
            return null;
        }
    }


    private CtClass addEquals(CtClass keyClass) throws CannotCompileException {
        StringBuffer method = new StringBuffer();
        method.append("public boolean equals(Object other) {\n");
        method.append("if (other == null) return false;\n");
        method.append("if (getClass() != other.getClass()) return false;\n");
        method.append(this.keyType + " otherKey = (" + this.keyType + ") other;\n");
        for (String field : this.fields) {
            method.append("if (!this." + field + ".equals(otherKey." + field + ")) return false;\n");
        }
        method.append("return true;\n");
        method.append("}\n");

        keyClass.addMethod(CtNewMethod.make(method.toString(), keyClass));
        return keyClass;
    }


    private CtClass addHashCode(CtClass keyClass) throws CannotCompileException {
        StringBuffer method = new StringBuffer();
        method.append("public int hashCode() {\n");
        method.append("int result = 0;\n");
        for (String field : this.fields) {
            method.append("result = 31 * result + (this." + field + " != null ? this." + field + ".hashCode() : 0);\n");
        }
        method.append("return result;\n");
        method.append("}\n");

        keyClass.addMethod(CtNewMethod.make(method.toString(), keyClass));
        return keyClass;
    }


    private CtClass addBuildMethod(ClassPool pool, CtClass keyClass) throws CannotCompileException {
        keyClass.setInterfaces(new CtClass[]{pool.makeClass("monevent.common.process.matching.memory.IMemoryCacheKey")});

        StringBuffer method = new StringBuffer();

        method.append("public monevent.common.process.matching.MatchingResult build () {\n");

        method.append("monevent.common.process.matching.MatchingResult result = new monevent.common.process.matching.MatchingResult(\"" + this.resultType + "\");\n");
        for (String field : this.fields) {
            method.append("result.setValue(\"" + field + "\",this." + field + ");\n");
        }

        method.append("return result;\n");
        method.append("}\n");

        keyClass.addMethod(CtNewMethod.make(method.toString(), keyClass));
        return keyClass;
    }


    private CtClass addProperty(String propertyName, ClassPool pool, CtClass keyClass) throws CannotCompileException, NotFoundException {
        keyClass.addField(new CtField(pool.get(Comparable.class.getName()), propertyName, keyClass));
        return keyClass;
    }


    private CtClass addSetValueMethod(CtClass keyClass) throws CannotCompileException {

        StringBuffer method = new StringBuffer();

        method.append("public void setValue(String field, Object value) {\n");
        for (String field : this.fields) {

            method.append("if (\"" + field + "\".equalsIgnoreCase(field)) {\n");
            method.append("this." + field + " = value;\n");
            method.append("}\n");
        }

        method.append("}\n");

        keyClass.addMethod(CtNewMethod.make(method.toString(), keyClass));
        return keyClass;
    }

    private String build(String type) {
        return type.substring(0, 1).toUpperCase().concat(type.substring(1, type.length())) + new UUID().toString().replace("-", "");
    }


}
