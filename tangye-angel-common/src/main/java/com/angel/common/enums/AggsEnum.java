package com.angel.common.enums;

public interface AggsEnum {

	enum Buckets implements AggsEnum{
		TERMS{
			@Override
			public String toString() {
				return "terms";
			}
		},
		HISTOGRAM{
			@Override
			public String toString() {
				return "histogram";
			}
		},
		DATE_HISTOGRAM{
			@Override
			public String toString() {
				return "date_histogram";
			}
		},
        INTERVAL{
            @Override
            public String toString() {
                return "interval";
            }
        },
		TIME_ZONE{
		    @Override
            public String toString(){
		        return "time_zone";
            }
        }
	}
	
	enum Metrics implements AggsEnum{
		MIN{
			public String toString() {
				return "min";
			}
		},
		MAX{
			public String toString() {
				return "max";
			}
		},
		AVG{
			public String toString() {
				return "avg";
			}
		},
		SUM{
			public String toString() {
				return "sum";
			}
		},
		STATS{
			public String toString() {
				return "stats";
			}
		},
        CARDINALITY{
		    public String toString(){
		        return "cardinality";
            }
        }
	}
}
