-- ----------------------------
-- Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `BLOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `CALENDAR_NAME` varchar(80) NOT NULL,
  `CALENDAR` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `CRON_EXPRESSION` varchar(80) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('测试webservice', 'taskGroup', '0 0 */1 * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('测试存储过程', 'taskGroup', '0 0/10 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `INSTANCE_NAME` varchar(80) NOT NULL,
  `FIRED_TIME` decimal(13,0) NOT NULL,
  `PRIORITY` decimal(13,0) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(80) DEFAULT NULL,
  `JOB_GROUP` varchar(80) DEFAULT NULL,
  `IS_STATEFUL` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `JOB_NAME` varchar(80) NOT NULL,
  `JOB_GROUP` varchar(80) NOT NULL,
  `DESCRIPTION` varchar(400) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(128) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `IS_STATEFUL` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('测试webservice', 'taskGroup', '测试webservice', 'com.universe.softplat.scheduler.job.WebServiceJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000C74000F6F7065726174696F6E4E616D655F777400047465737474000D727063557365724E616D655F7774000074000D72706350617373776F72645F7771007E000A740010696E707574506172616D56616C75657374000A68656C6C6F576F726C6474000A656E64706F696E745F77740036687474703A2F2F6C6F63616C686F73743A393039312F73736D4672616D652F73657276696365732F74657374576562736572766963657400087461736B547970657400013174000773636854797065740031636F6D2E756E6976657273652E736F6674706C61742E7363686564756C65722E6A6F622E576562536572766963654A6F6274001173696E676C655665726966794578705F77740004313D3D317400076372656174657274000561646D696E740006706172616D73707400087461736B4E616D65740010E6B58BE8AF9577656273657276696365740010657874656E644A6F6244657461696C737371007E00053F4000000000000C7708000000100000000671007E000771007E000871007E000971007E000A71007E000B71007E000A71007E000C71007E000D71007E000E71007E000F71007E001471007E0015787800);
INSERT INTO `qrtz_job_details` VALUES ('测试存储过程', 'taskGroup', '测试存储过程', 'com.universe.softplat.scheduler.job.ProcedureJob', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000187708000000200000000D74001173696E676C655665726966794578705F7074000752323D3D2231227400087461736B54797065740001317400076372656174657274000561646D696E740006706172616D737074000E64617461536F757263654E616D6574000074000870617373776F726474000373736D7400087461736B4E616D65740012E6B58BE8AF95E5AD98E582A8E8BF87E7A88B74000375726C7400296A6462633A6F7261636C653A7468696E3A403139322E3136382E322E3138303A313532313A6F72636C74000870726F634E616D6574001263616C6C20505F5445535428312C3F2C3F29740008757365724E616D6574000373736D74000773636854797065740030636F6D2E756E6976657273652E736F6674706C61742E7363686564756C65722E6A6F622E50726F6365647572654A6F62740010657874656E644A6F6244657461696C737371007E00053F4000000000000C7708000000100000000771007E000771007E000871007E001671007E001771007E001871007E001971007E000E71007E000F74000A6472697665724E616D6574001F6F7261636C652E6A6462632E6472697665722E4F7261636C6544726976657271007E001071007E001171007E001471007E00157871007E001E71007E001F7800);

-- ----------------------------
-- Table structure for `qrtz_job_listeners`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_listeners`;
CREATE TABLE `qrtz_job_listeners` (
  `JOB_NAME` varchar(80) NOT NULL,
  `JOB_GROUP` varchar(80) NOT NULL,
  `JOB_LISTENER` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_listeners
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `LOCK_NAME` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `TRIGGER_GROUP` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_scheduler_log`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_log`;
CREATE TABLE `qrtz_scheduler_log` (
  `PKID` varchar(64) NOT NULL,
  `TASK_NAME` varchar(80) NOT NULL,
  `CALL_METHOD` varchar(20) NOT NULL,
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `RESULT_FLAG` char(1) DEFAULT NULL,
  `LOG_LEVEL` char(1) DEFAULT NULL,
  `INFO` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度日志';

-- ----------------------------
-- Records of qrtz_scheduler_log
-- ----------------------------
INSERT INTO `qrtz_scheduler_log` VALUES ('589cc7f0-a135-4559-b905-65b4491bf2d3', '测试webservice', '调度中心', '2016-07-04 22:36:55', '2016-07-04 22:36:55', 'Y', 'N', '结果1:hello World!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('e196a5aa-e4d6-40aa-bbf1-2c0817d1be92', '测试webservice', '调度中心', '2016-07-04 22:43:27', '2016-07-04 22:43:27', 'Y', 'N', '结果1:hello World!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('a16f2c95-3dff-495b-98a1-b285d99924d6', '测试webservice', '调度中心', '2016-07-04 22:49:24', '2016-07-04 22:49:24', 'Y', 'N', '结果1:hello World!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('f7e40978-1381-4cca-8d78-f2e0fd2d3c83', '测试webservice', '调度中心', '2016-07-04 22:51:37', '2016-07-04 22:51:38', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('5d4ab6ec-8a39-4bc2-b287-7670bb95b664', '测试webservice', '调度中心', '2016-07-04 22:56:21', '2016-07-04 22:56:21', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('b8109dcf-35eb-4b21-82b6-e78d76e1e442', '测试webservice', '调度中心', '2016-07-04 22:59:45', '2016-07-04 22:59:46', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('4780ea3e-6f20-4ffb-963a-4bdb7d6537f1', '测试webservice', '调度中心', '2016-07-04 22:59:56', '2016-07-04 22:59:57', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('e88e25f8-1bf7-47f9-a6ab-82327aef140b', '测试webservice', '调度中心', '2016-07-04 22:59:59', '2016-07-04 23:00:00', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('f3a33b6e-4c5c-4f2c-a3fc-80df5e153104', '测试webservice', '调度中心', '2016-07-04 23:01:33', '2016-07-04 23:01:34', 'Y', 'N', '结果1:helloWorld!!!! ');
INSERT INTO `qrtz_scheduler_log` VALUES ('5b3b3bb1-148e-4144-aa73-416400d163e8', '测试webservice', '调度中心', '2016-07-04 23:02:38', '2016-07-04 23:02:38', 'Y', 'N', '结果1:helloWorld ');
INSERT INTO `qrtz_scheduler_log` VALUES ('abaf95ea-090c-41bd-bfa7-698e79608383', '测试存储过程', '调度中心', '2016-07-04 23:04:20', '2016-07-04 23:04:21', 'Y', 'N', '结果1:操作成功！ 结果2:1 ');
INSERT INTO `qrtz_scheduler_log` VALUES ('e23c7910-cde2-4e34-960b-66aba9a279ec', '测试存储过程', '调度中心', '2016-07-04 23:04:36', '2016-07-04 23:04:36', 'Y', 'N', '结果1:操作成功！ 结果2:1 ');
INSERT INTO `qrtz_scheduler_log` VALUES ('a04105d5-2f51-4aa3-af4b-e629c8864f34', '测试存储过程', '调度中心', '2016-07-04 23:04:57', '2016-07-04 23:04:58', 'Y', 'N', '结果1:操作成功！ 结果2:1 ');

-- ----------------------------
-- Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `INSTANCE_NAME` varchar(80) NOT NULL,
  `LAST_CHECKIN_TIME` decimal(13,0) NOT NULL,
  `CHECKIN_INTERVAL` decimal(13,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `REPEAT_COUNT` decimal(7,0) NOT NULL,
  `REPEAT_INTERVAL` decimal(12,0) NOT NULL,
  `TIMES_TRIGGERED` decimal(7,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `JOB_NAME` varchar(80) NOT NULL,
  `JOB_GROUP` varchar(80) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(120) DEFAULT NULL,
  `NEXT_FIRE_TIME` decimal(13,0) DEFAULT NULL,
  `PREV_FIRE_TIME` decimal(13,0) DEFAULT NULL,
  `PRIORITY` decimal(13,0) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` decimal(13,0) NOT NULL,
  `END_TIME` decimal(13,0) DEFAULT NULL,
  `CALENDAR_NAME` varchar(80) DEFAULT NULL,
  `MISFIRE_INSTR` decimal(2,0) DEFAULT NULL,
  `JOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('测试webservice', 'taskGroup', '测试webservice', 'taskGroup', '0', null, '1467648000000', '1467644400000', '5', 'WAITING', 'CRON', '1467642580000', '0', null, '2', '');
INSERT INTO `qrtz_triggers` VALUES ('测试存储过程', 'taskGroup', '测试存储过程', 'taskGroup', '0', null, '1467645000000', '-1', '5', 'WAITING', 'CRON', '1467644659000', '0', null, '2', '');

-- ----------------------------
-- Table structure for `qrtz_trigger_listeners`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_trigger_listeners`;
CREATE TABLE `qrtz_trigger_listeners` (
  `TRIGGER_NAME` varchar(80) NOT NULL,
  `TRIGGER_GROUP` varchar(80) NOT NULL,
  `TRIGGER_LISTENER` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;