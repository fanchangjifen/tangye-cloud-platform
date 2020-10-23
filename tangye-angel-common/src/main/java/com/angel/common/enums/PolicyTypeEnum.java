package com.angel.common.enums;

public enum PolicyTypeEnum {

    POLICY {
        @Override
        public String toString(){
            return "policy";
        }
    },
    ANALYSIS {
        @Override
        public String toString(){
            return "analysis";
        }
    },
    NOTICE {
        @Override
        public String toString(){
            return "notice";
        }
    };

    public abstract String toString();
}
