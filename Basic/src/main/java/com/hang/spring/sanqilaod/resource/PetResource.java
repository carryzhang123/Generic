package com.hang.spring.sanqilaod.resource;

import com.hang.spring.sanqilaod.annotation.Id;
import com.hang.spring.sanqilaod.annotation.Resource;

/**
 * @author ZhangHang
 * @create 2018-06-07 15:09
 **/
@Resource
public class PetResource{
        @Id
        private String account;
        private String serverId;
        private String time;
        private String level;
        private String exp;

        public String getServerId() {
            return serverId;
        }

        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
}
