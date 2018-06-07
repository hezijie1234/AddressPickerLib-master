package com.ywp.addresspickerdemo;

import java.util.List;

/**
 * Created by hezijie on 2018/6/6.
 */

public class CityInfo {

    private List<RECORDSBean> RECORDS;

    public List<RECORDSBean> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(List<RECORDSBean> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public static class RECORDSBean {
        /**
         * id : 110000
         * name : 北京市
         * arealevel : 1
         * parent_id : null
         */

        private int id;
        private String name;
        private int arealevel;
        private int parent_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getArealevel() {
            return arealevel;
        }

        public void setArealevel(int arealevel) {
            this.arealevel = arealevel;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }
}
