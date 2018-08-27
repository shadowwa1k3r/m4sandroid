package com.osg.loki.m4s.DataBase;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ergas on 1/27/2018.
 */

public class Migration implements RealmMigration{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion<62){
            RealmObjectSchema testSchema = schema.get("Test");

            testSchema.setRequired("test_name",false);
            testSchema.setRequired("test_description",false);
            testSchema.setRequired("q_content",false);
            testSchema.setRequired("a1",false);
            testSchema.setRequired("a2",false);
            testSchema.setRequired("a3",false);
            testSchema.setRequired("a4",false);
            oldVersion++;
        }

       if (oldVersion<70){
            RealmObjectSchema test = schema.get("Test");
            try {
                test.addField("a5",String.class);
                oldVersion++;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj instanceof Migration);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
