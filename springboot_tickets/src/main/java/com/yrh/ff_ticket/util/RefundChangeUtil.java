package com.yrh.ff_ticket.util;

public class RefundChangeUtil {
    public static final long M_14Day=1209600000;
    public static final long M_2Day=172800000;
    public static final long M_4Hour=1440000;
    public static double getRefundFee(long flyDateTime,long nowDateTime){
        long value=flyDateTime-nowDateTime;
        if(value>=M_14Day){
            return 0.4;
        }
        if(value>=M_2Day){
            return 0.6;
        }
        if(value>=M_4Hour){
            return 0.8;
        }
        return 1;
    }
    public static double getChangeFee(long flyDateTime,long nowDateTime){
        long value=flyDateTime-nowDateTime;
        if(value>=M_14Day){
            return 0.2;
        }
        if(value>=M_2Day){
            return 0.3;
        }
        if(value>=M_4Hour){
            return 0.5;
        }
        return 0.6;
    }
}
