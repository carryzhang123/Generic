package hang.pulllog.log.impl.handler;//package com.game.log.impl.handler;
//
//import com.game.log.LogFilterEnum;
//import com.game.log.impl.LogFilter;
//
///**
// * @author ZhangHang
// * @create 2018-05-09 14:26
// **/
//public class LogBeforeLevelHandler extends LogFilter {
//    @Override
//    public int getId() {
//        return LogFilterEnum.BEFORELEVEL.getId();
//    }
//
//    @Override
//    public boolean filter(String key, String value) {
//        if (key.equals("level") && value.equals("2")) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//}
