/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.17-log : Database - hs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `app_inpatient_consultation_apply` */

CREATE TABLE `app_inpatient_consultation_apply` (
  `result` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK21tv6talpc5obktlcdmf4x0ew` FOREIGN KEY (`id`) REFERENCES `domain_order_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_inspect_apply` */

CREATE TABLE `app_inspect_apply` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKk16infuqsx8rseihrehvo30sh` FOREIGN KEY (`id`) REFERENCES `domain_order_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_inspect_apply_item` */

CREATE TABLE `app_inspect_apply_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `execute_date` datetime(6) DEFAULT NULL,
  `inspect_place` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `plan_execute_date` datetime(6) DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `arrange_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK68lw5y6iq057tg43ik5cnbp0w` (`arrange_dept_id`),
  KEY `FK4ic8f8cfnapij96afpio1sl44` (`inspect_apply_id`),
  KEY `FKnkrxonopfbl37do7fqidlrf3q` (`inspect_dept_id`),
  KEY `FKmgpi0dykk88if3b1f7rw85lob` (`inspect_item_id`),
  CONSTRAINT `FK4ic8f8cfnapij96afpio1sl44` FOREIGN KEY (`inspect_apply_id`) REFERENCES `app_inspect_apply` (`id`),
  CONSTRAINT `FK68lw5y6iq057tg43ik5cnbp0w` FOREIGN KEY (`arrange_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKmgpi0dykk88if3b1f7rw85lob` FOREIGN KEY (`inspect_item_id`) REFERENCES `app_inspect_item` (`id`),
  CONSTRAINT `FKnkrxonopfbl37do7fqidlrf3q` FOREIGN KEY (`inspect_dept_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_inspect_item` */

CREATE TABLE `app_inspect_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1imxebjcwmh4ouixehj8n4nvp` (`charge_item_id`),
  CONSTRAINT `FK1imxebjcwmh4ouixehj8n4nvp` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_inspect_result` */

CREATE TABLE `app_inspect_result` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `result` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_apply_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKktw5e4h2hugkl20ebfycsjoyb` (`inspect_apply_id`),
  KEY `FK4me0ji2kx7rxm3p0r9vjbwk4f` (`inspect_apply_item_id`),
  KEY `FKholltq44ppounm4k9imn9hwhx` (`inspect_dept_id`),
  KEY `FKi3axgiavs1my9by4o6b2c4md4` (`visit_id`),
  CONSTRAINT `FK4me0ji2kx7rxm3p0r9vjbwk4f` FOREIGN KEY (`inspect_apply_item_id`) REFERENCES `app_inspect_apply_item` (`id`),
  CONSTRAINT `FKholltq44ppounm4k9imn9hwhx` FOREIGN KEY (`inspect_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKi3axgiavs1my9by4o6b2c4md4` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKktw5e4h2hugkl20ebfycsjoyb` FOREIGN KEY (`inspect_apply_id`) REFERENCES `app_inspect_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_outpatient_plan_record` */

CREATE TABLE `app_outpatient_plan_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `current_allot_number` int(11) DEFAULT NULL,
  `current_encounter_number` int(11) DEFAULT NULL,
  `doctor_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `free` bit(1) DEFAULT NULL,
  `max_allot_number` int(11) DEFAULT NULL,
  `plan_end_date` datetime(6) DEFAULT NULL,
  `plan_start_date` datetime(6) DEFAULT NULL,
  `room_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `doctor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `room_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `voucher_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfsi2plm6glwgp5mb2893mii99` (`doctor_id`),
  KEY `FKgv9n1fq6ekpcyf5ei1jvabodm` (`room_id`),
  KEY `FK1la38gihp0hn5qnk4c5lj4gsb` (`voucher_type_id`),
  CONSTRAINT `FK1la38gihp0hn5qnk4c5lj4gsb` FOREIGN KEY (`voucher_type_id`) REFERENCES `app_outpatient_voucher_type` (`id`),
  CONSTRAINT `FKfsi2plm6glwgp5mb2893mii99` FOREIGN KEY (`doctor_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKgv9n1fq6ekpcyf5ei1jvabodm` FOREIGN KEY (`room_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_outpatient_voucher` */

CREATE TABLE `app_outpatient_voucher` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `repeat_visit` bit(1) DEFAULT NULL,
  `visit_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_record_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs85fmg5xmypd13a9u677mtsve` (`plan_record_id`),
  KEY `FK2yua8ypep39y3qfa6mwvgc1gp` (`visit_id`),
  CONSTRAINT `FK2yua8ypep39y3qfa6mwvgc1gp` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKs85fmg5xmypd13a9u677mtsve` FOREIGN KEY (`plan_record_id`) REFERENCES `app_outpatient_plan_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_outpatient_voucher_type` */

CREATE TABLE `app_outpatient_voucher_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9exk2gm9ii4lbbs1obnnls5ma` (`charge_item_id`),
  CONSTRAINT `FK9exk2gm9ii4lbbs1obnnls5ma` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_assist_material` */

CREATE TABLE `app_pharmacy_assist_material` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnhi6ubny4ndcgmt3usqqstqm8` (`charge_item_id`),
  CONSTRAINT `FKnhi6ubny4ndcgmt3usqqstqm8` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_configure_fluid_batch` */

CREATE TABLE `app_pharmacy_configure_fluid_batch` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `begin_date` int(11) NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `end_date` int(11) NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_execute_date` int(11) NOT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkxtysup61yv928tyigncosop6` (`pharmacy_id`),
  CONSTRAINT `FKkxtysup61yv928tyigncosop6` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_configure_fluid_order` */

CREATE TABLE `app_pharmacy_configure_fluid_order` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `distribute_date` datetime(6) DEFAULT NULL,
  `execute_count` int(11) DEFAULT NULL,
  `finish_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `area_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `batch_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hhnqiau12ob1pplhr4kptmrd` (`area_id`),
  KEY `FKhgu17fnc478i7kxkm5ymqvldp` (`batch_id`),
  KEY `FKmxeixb39eldvjirw3ba4ho8l8` (`creator_id`),
  KEY `FK2itlk0gwapvhdi9o4ln87ctlb` (`pharmacy_id`),
  CONSTRAINT `FK2itlk0gwapvhdi9o4ln87ctlb` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK9hhnqiau12ob1pplhr4kptmrd` FOREIGN KEY (`area_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKhgu17fnc478i7kxkm5ymqvldp` FOREIGN KEY (`batch_id`) REFERENCES `app_pharmacy_configure_fluid_batch` (`id`),
  CONSTRAINT `FKmxeixb39eldvjirw3ba4ho8l8` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_dispense_drug_win` */

CREATE TABLE `app_pharmacy_dispense_drug_win` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr3sariyy94i5m469efx65u7ip` (`pharmacy_id`),
  CONSTRAINT `FKr3sariyy94i5m469efx65u7ip` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_dispensing_drug_batch` */

CREATE TABLE `app_pharmacy_dispensing_drug_batch` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `begin_date` int(11) NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `end_date` int(11) NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_execute_date` int(11) NOT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnh15rev3dm23uqyneh52350cf` (`pharmacy_id`),
  CONSTRAINT `FKnh15rev3dm23uqyneh52350cf` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_dispensing_drug_order` */

CREATE TABLE `app_pharmacy_dispensing_drug_order` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `execute_count` int(11) DEFAULT NULL,
  `finish_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `area_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `batch_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlfouj1wf3nvjwljepbesr09dm` (`area_id`),
  KEY `FKf3oc35s6im0wrkygrixil8wmy` (`batch_id`),
  KEY `FKjwahvhrt3w7h7ei03f7pftp5m` (`creator_id`),
  KEY `FKg55sgrl7ki46dfky6hmoerel5` (`pharmacy_id`),
  CONSTRAINT `FKf3oc35s6im0wrkygrixil8wmy` FOREIGN KEY (`batch_id`) REFERENCES `app_pharmacy_dispensing_drug_batch` (`id`),
  CONSTRAINT `FKg55sgrl7ki46dfky6hmoerel5` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKjwahvhrt3w7h7ei03f7pftp5m` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKlfouj1wf3nvjwljepbesr09dm` FOREIGN KEY (`area_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_purchase` */

CREATE TABLE `app_pharmacy_drug_purchase` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `count` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `drug_type_spec_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKheamy0yrv2i7ri4a6xr7k5arw` (`drug_type_spec_id`),
  CONSTRAINT `FKheamy0yrv2i7ri4a6xr7k5arw` FOREIGN KEY (`drug_type_spec_id`) REFERENCES `app_pharmacy_drug_type_spec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_type` */

CREATE TABLE `app_pharmacy_drug_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `production_batch` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `withhold` int(11) NOT NULL,
  `drug_purchase_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_spec_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKob0v5ysablp1td59f2d3eruh0` (`drug_purchase_id`),
  KEY `FKe2p8enuq6l36o5wxbvcqw0xp9` (`drug_type_spec_id`),
  KEY `FKso5fhnm22fwtdfngwfq002jc0` (`pharmacy_id`),
  CONSTRAINT `FKe2p8enuq6l36o5wxbvcqw0xp9` FOREIGN KEY (`drug_type_spec_id`) REFERENCES `app_pharmacy_drug_type_spec` (`id`),
  CONSTRAINT `FKob0v5ysablp1td59f2d3eruh0` FOREIGN KEY (`drug_purchase_id`) REFERENCES `app_pharmacy_drug_purchase` (`id`),
  CONSTRAINT `FKso5fhnm22fwtdfngwfq002jc0` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_type_consume_record` */

CREATE TABLE `app_pharmacy_drug_type_consume_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `drug_order_execute_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_order_type_app_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6c4bhyqpoem849g85jm3gujnw` (`drug_order_execute_id`),
  KEY `FKoaxe5xupd991v7d1gbqi9lij0` (`drug_order_type_app_id`),
  KEY `FK5gk9w1oi1mmgf3ma748r8iqe3` (`drug_type_id`),
  CONSTRAINT `FK5gk9w1oi1mmgf3ma748r8iqe3` FOREIGN KEY (`drug_type_id`) REFERENCES `app_pharmacy_drug_type` (`id`),
  CONSTRAINT `FK6c4bhyqpoem849g85jm3gujnw` FOREIGN KEY (`drug_order_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKoaxe5xupd991v7d1gbqi9lij0` FOREIGN KEY (`drug_order_type_app_id`) REFERENCES `domain_order_type_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_type_spec` */

CREATE TABLE `app_pharmacy_drug_type_spec` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `effect` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `is_transport_fluid_charge` bit(1) DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjmnk9r8uw9m9c37t82nh3ymn5` (`charge_item_id`),
  KEY `FK7j0ory2reu5en9e1n5xpnwgmj` (`parent_id`),
  CONSTRAINT `FK7j0ory2reu5en9e1n5xpnwgmj` FOREIGN KEY (`parent_id`) REFERENCES `app_pharmacy_drug_type_spec` (`id`),
  CONSTRAINT `FKjmnk9r8uw9m9c37t82nh3ymn5` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_use_mode` */

CREATE TABLE `app_pharmacy_drug_use_mode` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_drug_use_mode_charge_item` */

CREATE TABLE `app_pharmacy_drug_use_mode_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_mode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `sign` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `assist_material_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `use_mode_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhvfievfvl0yq5u8fw8ykpecsy` (`assist_material_id`),
  KEY `FKs3pfji827ourxnkwqv3k2paur` (`use_mode_id`),
  CONSTRAINT `FKhvfievfvl0yq5u8fw8ykpecsy` FOREIGN KEY (`assist_material_id`) REFERENCES `app_pharmacy_assist_material` (`id`),
  CONSTRAINT `FKs3pfji827ourxnkwqv3k2paur` FOREIGN KEY (`use_mode_id`) REFERENCES `app_pharmacy_drug_use_mode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_prescription` */

CREATE TABLE `app_pharmacy_prescription` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `illustrate` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `template` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8jcto47gf9hgiuc2phig44xhe` (`creator_id`),
  KEY `FK7yy7c40wq3fjhx2omp8by4auf` (`visit_id`),
  CONSTRAINT `FK7yy7c40wq3fjhx2omp8by4auf` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FK8jcto47gf9hgiuc2phig44xhe` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_prescription_diagnosis_value` */

CREATE TABLE `app_pharmacy_prescription_diagnosis_value` (
  `prescription_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `diagnosis_value_id` varchar(36) COLLATE utf8_bin NOT NULL,
  KEY `FKdxjlvgoae7xx7pcerw5y88d80` (`diagnosis_value_id`),
  KEY `FK7q2jq3ml4gd8lfl3op4r0uw06` (`prescription_id`),
  CONSTRAINT `FK7q2jq3ml4gd8lfl3op4r0uw06` FOREIGN KEY (`prescription_id`) REFERENCES `app_pharmacy_prescription` (`id`),
  CONSTRAINT `FKdxjlvgoae7xx7pcerw5y88d80` FOREIGN KEY (`diagnosis_value_id`) REFERENCES `domain_treatment_item_value` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_pharmacy_prescription_order` */

CREATE TABLE `app_pharmacy_prescription_order` (
  `prescription_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `order_id` varchar(36) COLLATE utf8_bin NOT NULL,
  KEY `FK3asuy4p4nvw28fb5njf3n9k7y` (`order_id`),
  KEY `FK47pfmqoasjtkcf9iy7s26phbq` (`prescription_id`),
  CONSTRAINT `FK3asuy4p4nvw28fb5njf3n9k7y` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`),
  CONSTRAINT `FK47pfmqoasjtkcf9iy7s26phbq` FOREIGN KEY (`prescription_id`) REFERENCES `app_pharmacy_prescription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_recordroom_medical_case` */

CREATE TABLE `app_recordroom_medical_case` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `case_number` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `position` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `clip_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb6g5coekb7n0skq6m35puip04` (`clip_id`),
  KEY `FKsxrrg67exh0ox0x3dlx5w84ep` (`creator_id`),
  KEY `FKmi2fvndq6s2vtvmydrtpwcdlt` (`visit_id`),
  CONSTRAINT `FKb6g5coekb7n0skq6m35puip04` FOREIGN KEY (`clip_id`) REFERENCES `domain_medical_record_clip` (`id`),
  CONSTRAINT `FKmi2fvndq6s2vtvmydrtpwcdlt` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKsxrrg67exh0ox0x3dlx5w84ep` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_surgery_apply` */

CREATE TABLE `app_surgery_apply` (
  `surgery_place` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `arrange_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `surgery_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3m2ok3r5hl4oru1k7e6vu043y` (`arrange_dept_id`),
  KEY `FKb2i3vfp53fvbnro7vxr7f3t9a` (`surgery_dept_id`),
  CONSTRAINT `FK3m2ok3r5hl4oru1k7e6vu043y` FOREIGN KEY (`arrange_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK65cl6qx90ar7vo6e9dphpj1r9` FOREIGN KEY (`id`) REFERENCES `domain_order_apply` (`id`),
  CONSTRAINT `FKb2i3vfp53fvbnro7vxr7f3t9a` FOREIGN KEY (`surgery_dept_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_surgery_apply_item` */

CREATE TABLE `app_surgery_apply_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `surgery_apply_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `surgery_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK78plonvg8krjf55krnni6q9dv` (`surgery_apply_id`,`surgery_type_id`),
  KEY `FKqvmyvgx6dnnt92qmr1u2qlxvg` (`surgery_type_id`),
  CONSTRAINT `FKogxnjbii9y6jkwhkmtlqwv066` FOREIGN KEY (`surgery_apply_id`) REFERENCES `app_surgery_apply` (`id`),
  CONSTRAINT `FKqvmyvgx6dnnt92qmr1u2qlxvg` FOREIGN KEY (`surgery_type_id`) REFERENCES `app_surgery_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `app_surgery_type` */

CREATE TABLE `app_surgery_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfpw1orc5gxx57k7exw9mnmy2p` (`charge_item_id`),
  KEY `FKe2xqjxfuo810fum2n00gnrfu2` (`parent_id`),
  CONSTRAINT `FKe2xqjxfuo810fum2n00gnrfu2` FOREIGN KEY (`parent_id`) REFERENCES `app_surgery_type` (`id`),
  CONSTRAINT `FKfpw1orc5gxx57k7exw9mnmy2p` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_charge_bill` */

CREATE TABLE `domain_cost_charge_bill` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `balance` float NOT NULL,
  `charge_mode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `consume` float NOT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5dpr08ko3x0pxi7h0c6lq1g8` (`visit_id`),
  CONSTRAINT `FKe5dpr08ko3x0pxi7h0c6lq1g8` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_charge_item` */

CREATE TABLE `domain_cost_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charging_mode` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `price` float NOT NULL,
  `unit` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_charge_record` */

CREATE TABLE `domain_cost_charge_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `amount` float DEFAULT NULL,
  `charge_item_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `belong_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `charge_bill_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `cost_record_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_execute_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `original_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK10qu39mcvofgortrdv20ecvf3` (`belong_dept_id`),
  KEY `FKfd7q2xmctjw0p70a0lwtwnc3a` (`charge_bill_id`),
  KEY `FKpkfkyi1h34sl833v9sse2ii2q` (`charge_dept_id`),
  KEY `FKm37vf90d5yrp99fchyb9uk8nx` (`charge_item_id`),
  KEY `FK5yym1pbq4bbqa40idpql887vb` (`cost_record_id`),
  KEY `FK9pumskewa8sidki9fbcuwmswl` (`order_execute_id`),
  KEY `FK7bemo6sns2804nolq79apvxdv` (`original_id`),
  KEY `FK66wuow5ooe2eabx5mrbsn5it8` (`visit_id`),
  CONSTRAINT `FK10qu39mcvofgortrdv20ecvf3` FOREIGN KEY (`belong_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK5yym1pbq4bbqa40idpql887vb` FOREIGN KEY (`cost_record_id`) REFERENCES `domain_cost_cost_record` (`id`),
  CONSTRAINT `FK66wuow5ooe2eabx5mrbsn5it8` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FK7bemo6sns2804nolq79apvxdv` FOREIGN KEY (`original_id`) REFERENCES `domain_cost_charge_record` (`id`),
  CONSTRAINT `FK9pumskewa8sidki9fbcuwmswl` FOREIGN KEY (`order_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKfd7q2xmctjw0p70a0lwtwnc3a` FOREIGN KEY (`charge_bill_id`) REFERENCES `domain_cost_charge_bill` (`id`),
  CONSTRAINT `FKm37vf90d5yrp99fchyb9uk8nx` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`),
  CONSTRAINT `FKpkfkyi1h34sl833v9sse2ii2q` FOREIGN KEY (`charge_dept_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_cost_record` */

CREATE TABLE `domain_cost_cost_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `cost` float NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_cost_visit_charge_item` */

CREATE TABLE `domain_cost_visit_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqo7a1f9ki7euj5e6k5db56eb5` (`charge_item_id`),
  KEY `FK29p7cq41av6dae0lawvxb1k3a` (`visit_id`),
  CONSTRAINT `FK29p7cq41av6dae0lawvxb1k3a` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKqo7a1f9ki7euj5e6k5db56eb5` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_disease` */

CREATE TABLE `domain_disease` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `disease_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg2sum0fax8fdc7nq052o88ug1` (`disease_type_id`),
  CONSTRAINT `FKg2sum0fax8fdc7nq052o88ug1` FOREIGN KEY (`disease_type_id`) REFERENCES `domain_disease_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_disease_type` */

CREATE TABLE `domain_disease_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbgb23khivk4gqohvua5esefds` (`parent_id`),
  CONSTRAINT `FKbgb23khivk4gqohvua5esefds` FOREIGN KEY (`parent_id`) REFERENCES `domain_disease_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record` */

CREATE TABLE `domain_medical_record` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `doctor_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `sign_doctor_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `type_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `builder_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `clip_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `doctor_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `sign_doctor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `type_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrewl82ewccalg3nx582mn041k` (`builder_id`),
  KEY `FKgfk3fau9lhug1u9luhlhn2knn` (`clip_id`),
  KEY `FK2cwpf57lnei4gfximmkbi0xiq` (`doctor_id`),
  KEY `FKfhpsmkraeqm8krlh78apybs91` (`sign_doctor_id`),
  KEY `FK4kq1is6ufnqp1ecv6uqx48vn9` (`type_id`),
  KEY `FKfks76hm36muv4orsvsemvl95s` (`visit_id`),
  CONSTRAINT `FK2cwpf57lnei4gfximmkbi0xiq` FOREIGN KEY (`doctor_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FK4kq1is6ufnqp1ecv6uqx48vn9` FOREIGN KEY (`type_id`) REFERENCES `domain_medical_record_type` (`id`),
  CONSTRAINT `FKfhpsmkraeqm8krlh78apybs91` FOREIGN KEY (`sign_doctor_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKfks76hm36muv4orsvsemvl95s` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKgfk3fau9lhug1u9luhlhn2knn` FOREIGN KEY (`clip_id`) REFERENCES `domain_medical_record_clip` (`id`),
  CONSTRAINT `FKrewl82ewccalg3nx582mn041k` FOREIGN KEY (`builder_id`) REFERENCES `domain_medical_record_builder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_builder` */

CREATE TABLE `domain_medical_record_builder` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `inspect_result_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK80imo31agvlwgp6p70maiob78` (`visit_id`),
  KEY `FKdlclaa5pscvqtqg9iumn67ymb` (`inspect_result_id`),
  KEY `FKsigltawdaktic54uodx5ks5j7` (`type_id`),
  CONSTRAINT `FK80imo31agvlwgp6p70maiob78` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKdlclaa5pscvqtqg9iumn67ymb` FOREIGN KEY (`inspect_result_id`) REFERENCES `app_inspect_result` (`id`),
  CONSTRAINT `FKsigltawdaktic54uodx5ks5j7` FOREIGN KEY (`type_id`) REFERENCES `domain_medical_record_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_clip` */

CREATE TABLE `domain_medical_record_clip` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `check_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `checker_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2i07w9ereuxya7v200im8gxww` (`check_dept_id`),
  KEY `FKtkidb3nskd17rybx5kocrwii3` (`checker_id`),
  KEY `FK62ax1idgg7ea9yu773p4tcclx` (`visit_id`),
  CONSTRAINT `FK2i07w9ereuxya7v200im8gxww` FOREIGN KEY (`check_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK62ax1idgg7ea9yu773p4tcclx` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKtkidb3nskd17rybx5kocrwii3` FOREIGN KEY (`checker_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_item` */

CREATE TABLE `domain_medical_record_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `record_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `treatment_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKogwo2wry9g8m5ga6jjgmsvdm3` (`record_id`),
  KEY `FK242h4cfwr912922513o3s2q9f` (`treatment_item_id`),
  KEY `FK9m22d9dteenpal5q5vvyxy49e` (`visit_id`),
  CONSTRAINT `FK242h4cfwr912922513o3s2q9f` FOREIGN KEY (`treatment_item_id`) REFERENCES `domain_treatment_item` (`id`),
  CONSTRAINT `FK9m22d9dteenpal5q5vvyxy49e` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKogwo2wry9g8m5ga6jjgmsvdm3` FOREIGN KEY (`record_id`) REFERENCES `domain_medical_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_item_value` */

CREATE TABLE `domain_medical_record_item_value` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `remark` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `info` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `disease_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKivwqacchl15m9va6qmpnmpy9c` (`item_id`),
  KEY `FKqb1v9cnti2glxb6hkyisrb8kx` (`visit_id`),
  KEY `FKl6chcafv3hyeupgt0xfkrtqan` (`dept_id`),
  KEY `FKqxy9n0m38y3oixb9t82352r58` (`disease_id`),
  CONSTRAINT `FKivwqacchl15m9va6qmpnmpy9c` FOREIGN KEY (`item_id`) REFERENCES `domain_medical_record_item` (`id`),
  CONSTRAINT `FKl6chcafv3hyeupgt0xfkrtqan` FOREIGN KEY (`dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKqb1v9cnti2glxb6hkyisrb8kx` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKqxy9n0m38y3oixb9t82352r58` FOREIGN KEY (`disease_id`) REFERENCES `domain_disease` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_item_value_list` */

CREATE TABLE `domain_medical_record_item_value_list` (
  `value_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `data` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `data_key` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`value_id`,`data_key`),
  CONSTRAINT `FKar3vet0twf391c0ycldromp0` FOREIGN KEY (`value_id`) REFERENCES `domain_medical_record_item_value` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_log` */

CREATE TABLE `domain_medical_record_log` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `template` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `operator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `record_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo69cq3ejtl5u5rapbv0c7qljd` (`operator_id`),
  KEY `FK8kxoxrf85c587i85bql1ppqrt` (`record_id`),
  CONSTRAINT `FK8kxoxrf85c587i85bql1ppqrt` FOREIGN KEY (`record_id`) REFERENCES `domain_medical_record` (`id`),
  CONSTRAINT `FKo69cq3ejtl5u5rapbv0c7qljd` FOREIGN KEY (`operator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_render` */

CREATE TABLE `domain_medical_record_render` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcovwgqfg01md3ujnm9usiahj9` (`type_id`),
  CONSTRAINT `FKcovwgqfg01md3ujnm9usiahj9` FOREIGN KEY (`type_id`) REFERENCES `domain_medical_record_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_type` */

CREATE TABLE `domain_medical_record_type` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `need_create` bit(1) DEFAULT NULL,
  `need_sign` bit(1) DEFAULT NULL,
  `template` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `is_unique` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_medical_record_type_item` */

CREATE TABLE `domain_medical_record_type_item` (
  `type_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `item_id` varchar(36) COLLATE utf8_bin NOT NULL,
  KEY `FKodedonbv6vhjrjias2q4a9vu2` (`item_id`),
  KEY `FK5bm3bjgntkgdqo8wmr2bj9e2i` (`type_id`),
  CONSTRAINT `FK5bm3bjgntkgdqo8wmr2bj9e2i` FOREIGN KEY (`type_id`) REFERENCES `domain_medical_record_type` (`id`),
  CONSTRAINT `FKodedonbv6vhjrjias2q4a9vu2` FOREIGN KEY (`item_id`) REFERENCES `domain_treatment_spec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order` */

CREATE TABLE `domain_order` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `belong_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `order_describe` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `execute_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `execute_need_send` bit(1) DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `place_type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_start_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state_desc` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `plan_end_date` datetime(6) DEFAULT NULL,
  `execute_date` datetime(6) DEFAULT NULL,
  `belong_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `compsite_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `execute_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `last_execute_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `type_app_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `frequency_type_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `execute_user_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXqdhfamneknwpg1g3xy3n0jp3t` (`state`),
  KEY `IDXmnjpmylsj3pq2ka8v3vutj2tp` (`belong_dept_id`),
  KEY `IDX4vmps7d0n5kx9w6cluq9bg5bm` (`execute_dept_id`),
  KEY `IDXopavmw5saodyk2gclgikw2phw` (`plan_start_date`),
  KEY `IDX72anlcavf7i5b6fi565yqvyh5` (`place_type`),
  KEY `FKrnshg3c00i6x62hmwv5w2mcaj` (`compsite_order_id`),
  KEY `FKblah3oba5fpiqin9ow7wtw5iq` (`creator_id`),
  KEY `FKhv5y2jqk34to8gkx90unc3f4f` (`last_execute_id`),
  KEY `FKcwh7ivmt8bnkbdenm097hani0` (`order_type_id`),
  KEY `FK155i7ikgd3gkngqm3q38g64t8` (`type_app_id`),
  KEY `FKfubo6tl05053on1owj3j3uxl3` (`visit_id`),
  KEY `FKahjcyqmcn0p3gh7lfgbc1ghch` (`frequency_type_id`),
  KEY `FKkmbtk5c1iplv15jr2e0cbguka` (`execute_user_id`),
  CONSTRAINT `FK155i7ikgd3gkngqm3q38g64t8` FOREIGN KEY (`type_app_id`) REFERENCES `domain_order_type_app` (`id`),
  CONSTRAINT `FKahjcyqmcn0p3gh7lfgbc1ghch` FOREIGN KEY (`frequency_type_id`) REFERENCES `domain_order_frequency_type` (`id`),
  CONSTRAINT `FKblah3oba5fpiqin9ow7wtw5iq` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKcwh7ivmt8bnkbdenm097hani0` FOREIGN KEY (`order_type_id`) REFERENCES `domain_order_type` (`id`),
  CONSTRAINT `FKfubo6tl05053on1owj3j3uxl3` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKhv5y2jqk34to8gkx90unc3f4f` FOREIGN KEY (`last_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKi1hihnk6lvi7ehk6l7tve61wx` FOREIGN KEY (`belong_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKkmbtk5c1iplv15jr2e0cbguka` FOREIGN KEY (`execute_user_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKrji202nx9u97nuk9cx43boxqm` FOREIGN KEY (`execute_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKrnshg3c00i6x62hmwv5w2mcaj` FOREIGN KEY (`compsite_order_id`) REFERENCES `domain_order_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_apply` */

CREATE TABLE `domain_order_apply` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `execute_date` datetime(6) DEFAULT NULL,
  `goal` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `plan_execute_date` datetime(6) DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk5copi399q78crvxa871kx1y7` (`order_id`),
  KEY `FKdthg5na4prbg03ljfhe97dfo6` (`visit_id`),
  CONSTRAINT `FKdthg5na4prbg03ljfhe97dfo6` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKk5copi399q78crvxa871kx1y7` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_execute` */

CREATE TABLE `domain_order_execute` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `actual_executor_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `belong_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `cost_state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `execute_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `is_alone` bit(1) DEFAULT NULL,
  `is_last` bit(1) DEFAULT NULL,
  `is_main` bit(1) DEFAULT NULL,
  `order_category` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `plan_end_date` datetime(6) DEFAULT NULL,
  `plan_start_date` datetime(6) DEFAULT NULL,
  `send_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `team_sequence` int(11) DEFAULT NULL,
  `tip` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `actual_executor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `belong_dept_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `charge_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `compsite_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `execute_dept_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `role_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `next_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `previous_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `team_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `inspect_apply_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_spec_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `fluid_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dispense_drug_win_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dispense_drug_order_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `fluid_order2_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXmqhmf9r5uay82l65b09yv28gg` (`state`),
  KEY `IDX7tabjdqn9b4omuphop16cg83m` (`belong_dept_id`),
  KEY `IDXtexikh7rllqiunytjjmy6lh7b` (`execute_dept_id`),
  KEY `IDXj3r5ujx8c1ci7yocsisvjsjls` (`plan_start_date`),
  KEY `IDXccjho4wsrcwmj83298mg1jxbd` (`charge_state`),
  KEY `FKtpavkj176k2t426g35kwf8oye` (`actual_executor_id`),
  KEY `FK3wr4t9au5ka4emwem7sr5b79p` (`charge_dept_id`),
  KEY `FK9iiq46wr3xext5vdker6pqfm1` (`compsite_order_id`),
  KEY `FKk4v5g54trq8pgm3tvoiewtvo5` (`role_id`),
  KEY `FKak1jebdhlhxwdtklpicv1irf9` (`next_id`),
  KEY `FKsr8s2hdc8p718gbwont4ig65` (`order_id`),
  KEY `FK15b0wbjdquv39s3gghnafgcqd` (`previous_id`),
  KEY `FK9myvc1mkcv5ci4jo7wpy36ek7` (`team_id`),
  KEY `FKdlkmr6k3dlhuyann8era05nd` (`visit_id`),
  KEY `FK8p56s7gkdkdso3y2o4nldxhqy` (`inspect_apply_item_id`),
  KEY `FKa1jncn55pmahvvfedq3j1r9n7` (`drug_type_spec_id`),
  KEY `FKm4ehhcl689q2vo7c3kl2448ua` (`pharmacy_id`),
  KEY `FK4jv61lm3uh3fu3rc8ygbbkj7y` (`fluid_order_id`),
  KEY `FKejx0i3iw8r1v254ngbar9rruo` (`dispense_drug_win_id`),
  KEY `FK2v4g05c8vfie7nqhvmqr51i92` (`dispense_drug_order_id`),
  KEY `FK82lr3v6oph504hobdyoo81xc0` (`fluid_order2_id`),
  CONSTRAINT `FK15b0wbjdquv39s3gghnafgcqd` FOREIGN KEY (`previous_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FK2v4g05c8vfie7nqhvmqr51i92` FOREIGN KEY (`dispense_drug_order_id`) REFERENCES `app_pharmacy_dispensing_drug_order` (`id`),
  CONSTRAINT `FK3wr4t9au5ka4emwem7sr5b79p` FOREIGN KEY (`charge_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK4jv61lm3uh3fu3rc8ygbbkj7y` FOREIGN KEY (`fluid_order_id`) REFERENCES `app_pharmacy_configure_fluid_order` (`id`),
  CONSTRAINT `FK82lr3v6oph504hobdyoo81xc0` FOREIGN KEY (`fluid_order2_id`) REFERENCES `app_pharmacy_configure_fluid_order` (`id`),
  CONSTRAINT `FK8p56s7gkdkdso3y2o4nldxhqy` FOREIGN KEY (`inspect_apply_item_id`) REFERENCES `app_inspect_apply_item` (`id`),
  CONSTRAINT `FK9iiq46wr3xext5vdker6pqfm1` FOREIGN KEY (`compsite_order_id`) REFERENCES `domain_order_team` (`id`),
  CONSTRAINT `FK9myvc1mkcv5ci4jo7wpy36ek7` FOREIGN KEY (`team_id`) REFERENCES `domain_order_execute_team` (`id`),
  CONSTRAINT `FKa1jncn55pmahvvfedq3j1r9n7` FOREIGN KEY (`drug_type_spec_id`) REFERENCES `app_pharmacy_drug_type_spec` (`id`),
  CONSTRAINT `FKak1jebdhlhxwdtklpicv1irf9` FOREIGN KEY (`next_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKdlkmr6k3dlhuyann8era05nd` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKejx0i3iw8r1v254ngbar9rruo` FOREIGN KEY (`dispense_drug_win_id`) REFERENCES `app_pharmacy_dispense_drug_win` (`id`),
  CONSTRAINT `FKew0l01s2tijc8gmcoxl9mqt72` FOREIGN KEY (`execute_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKk4v5g54trq8pgm3tvoiewtvo5` FOREIGN KEY (`role_id`) REFERENCES `domain_organization_role` (`id`),
  CONSTRAINT `FKm4ehhcl689q2vo7c3kl2448ua` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKsr8s2hdc8p718gbwont4ig65` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`),
  CONSTRAINT `FKtce8w7ihtjpfx5v17rr9mpoay` FOREIGN KEY (`belong_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKtpavkj176k2t426g35kwf8oye` FOREIGN KEY (`actual_executor_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_execute_charge_item` */

CREATE TABLE `domain_order_execute_charge_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `count` int(11) DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `order_execute_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkniybdf4ktr42xih1yar0t2nu` (`charge_item_id`),
  KEY `FKe624j8i8x2bbllu038sxvj9vb` (`order_execute_id`),
  CONSTRAINT `FKe624j8i8x2bbllu038sxvj9vb` FOREIGN KEY (`order_execute_id`) REFERENCES `domain_order_execute` (`id`),
  CONSTRAINT `FKkniybdf4ktr42xih1yar0t2nu` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_execute_team` */

CREATE TABLE `domain_order_execute_team` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_frequency_type` */

CREATE TABLE `domain_order_frequency_type` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `one` int(11) DEFAULT NULL,
  `two` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_interaction` */

CREATE TABLE `domain_order_interaction` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `order_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa11qij6blc1ntrdmdpojjb6uo` (`creator_id`),
  KEY `FK851igkfnaesyuejj88ubqo48p` (`order_id`),
  CONSTRAINT `FK851igkfnaesyuejj88ubqo48p` FOREIGN KEY (`order_id`) REFERENCES `domain_order` (`id`),
  CONSTRAINT `FKa11qij6blc1ntrdmdpojjb6uo` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_team` */

CREATE TABLE `domain_order_team` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2scdk7dxlug69et2h3aj3c5e` (`creator_id`),
  CONSTRAINT `FK2scdk7dxlug69et2h3aj3c5e` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_type` */

CREATE TABLE `domain_order_type` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `nursing_type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `charge_item_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `drug_type_spec_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK16q6wgw59ygw6wxypb500i1c` (`charge_item_id`),
  KEY `FK98qj8o4mygc8ej0u8jyuil8gq` (`parent_id`),
  KEY `FK37soq9vvrhr34cwpc95wvdcir` (`drug_type_spec_id`),
  CONSTRAINT `FK16q6wgw59ygw6wxypb500i1c` FOREIGN KEY (`charge_item_id`) REFERENCES `domain_cost_charge_item` (`id`),
  CONSTRAINT `FK37soq9vvrhr34cwpc95wvdcir` FOREIGN KEY (`drug_type_spec_id`) REFERENCES `app_pharmacy_drug_type_spec` (`id`),
  CONSTRAINT `FK98qj8o4mygc8ej0u8jyuil8gq` FOREIGN KEY (`parent_id`) REFERENCES `domain_order_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_order_type_app` */

CREATE TABLE `domain_order_type_app` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `category` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `drug_use_mode_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `pharmacy_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf8hv5o95xk6ooudt364kv57kr` (`drug_use_mode_id`),
  KEY `FKgyx51f4cyclqq1cn8x6sf5iln` (`pharmacy_id`),
  CONSTRAINT `FKf8hv5o95xk6ooudt364kv57kr` FOREIGN KEY (`drug_use_mode_id`) REFERENCES `app_pharmacy_drug_use_mode` (`id`),
  CONSTRAINT `FKgyx51f4cyclqq1cn8x6sf5iln` FOREIGN KEY (`pharmacy_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_organization_area_dept` */

CREATE TABLE `domain_organization_area_dept` (
  `area_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin NOT NULL,
  KEY `FKgrxa7xk3juy33i3vj47krgevx` (`dept_id`),
  KEY `FKcseeo00i2jxy5wv0pv41uputc` (`area_id`),
  CONSTRAINT `FKcseeo00i2jxy5wv0pv41uputc` FOREIGN KEY (`area_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKgrxa7xk3juy33i3vj47krgevx` FOREIGN KEY (`dept_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_organization_org_extend` */

CREATE TABLE `domain_organization_org_extend` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `in_charge_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `in_patient_office_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `org_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `out_charge_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `record_room_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKea8bxqnfiaagwikdt1anhs7wc` (`in_charge_dept_id`),
  KEY `FKkeni6wvorc5x5pyvy49250vgr` (`in_patient_office_dept_id`),
  KEY `FKdc3ue2mhgsirq46ym2dv5v933` (`org_id`),
  KEY `FK75m9uyqx0mmyjpko62496q4s4` (`out_charge_dept_id`),
  KEY `FK56k3v0j96md70apjcsfp7fiuw` (`record_room_dept_id`),
  CONSTRAINT `FK56k3v0j96md70apjcsfp7fiuw` FOREIGN KEY (`record_room_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FK75m9uyqx0mmyjpko62496q4s4` FOREIGN KEY (`out_charge_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKdc3ue2mhgsirq46ym2dv5v933` FOREIGN KEY (`org_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKea8bxqnfiaagwikdt1anhs7wc` FOREIGN KEY (`in_charge_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKkeni6wvorc5x5pyvy49250vgr` FOREIGN KEY (`in_patient_office_dept_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_organization_role` */

CREATE TABLE `domain_organization_role` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_organization_unit` */

CREATE TABLE `domain_organization_unit` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `org_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtnrmh1f0n2tugbaukr1j0un6r` (`parent_id`),
  KEY `FKhry5rfqf540a1rka4m0sq94w1` (`org_id`),
  CONSTRAINT `FKhry5rfqf540a1rka4m0sq94w1` FOREIGN KEY (`org_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKtnrmh1f0n2tugbaukr1j0un6r` FOREIGN KEY (`parent_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_organization_user` */

CREATE TABLE `domain_organization_user` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `org_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `superior_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtdmqr3fl619mu2jqo2n20lw0x` (`org_id`),
  KEY `FK7oc8t80d4p42puxtkq50lxhfa` (`dept_id`),
  KEY `FKee2xk192iyi8q2705kkn2l20h` (`superior_id`),
  CONSTRAINT `FK7oc8t80d4p42puxtkq50lxhfa` FOREIGN KEY (`dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKee2xk192iyi8q2705kkn2l20h` FOREIGN KEY (`superior_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKtdmqr3fl619mu2jqo2n20lw0x` FOREIGN KEY (`org_id`) REFERENCES `domain_organization_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_patient` */

CREATE TABLE `domain_patient` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `card_number` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXg354fdqlert7b5nxhlm9q5g2c` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_treatment_item` */

CREATE TABLE `domain_treatment_item` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `treatment_spec_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKadprk8jojs1u4chukrryoo1gm` (`creator_id`),
  KEY `FKkukfwa8miak1l4y6s5o06li60` (`treatment_spec_id`),
  KEY `FKayc1yfm8t87d8iy4a5apo0oqd` (`visit_id`),
  CONSTRAINT `FKadprk8jojs1u4chukrryoo1gm` FOREIGN KEY (`creator_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKayc1yfm8t87d8iy4a5apo0oqd` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKkukfwa8miak1l4y6s5o06li60` FOREIGN KEY (`treatment_spec_id`) REFERENCES `domain_treatment_spec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_treatment_item_value` */

CREATE TABLE `domain_treatment_item_value` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `treatment_spec_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `info` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `item_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `treatment_spec_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `disease_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjgkjed8y0af9xd70bino456kw` (`item_id`),
  KEY `FKr0w0gaq2g3duqrilxlgaavvdt` (`treatment_spec_id`),
  KEY `FKlkac6eitsvh0qlaycay3ljsc2` (`visit_id`),
  KEY `FKcygqam0ulomxa5xamlxr94vhk` (`disease_id`),
  CONSTRAINT `FKcygqam0ulomxa5xamlxr94vhk` FOREIGN KEY (`disease_id`) REFERENCES `domain_disease` (`id`),
  CONSTRAINT `FKjgkjed8y0af9xd70bino456kw` FOREIGN KEY (`item_id`) REFERENCES `domain_treatment_item` (`id`),
  CONSTRAINT `FKlkac6eitsvh0qlaycay3ljsc2` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKr0w0gaq2g3duqrilxlgaavvdt` FOREIGN KEY (`treatment_spec_id`) REFERENCES `domain_treatment_spec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_treatment_item_value_list` */

CREATE TABLE `domain_treatment_item_value_list` (
  `value_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `data` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `data_key` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`value_id`,`data_key`),
  CONSTRAINT `FK6kr1o48lceyykpiaw6o18fy1p` FOREIGN KEY (`value_id`) REFERENCES `domain_treatment_item_value` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_treatment_spec` */

CREATE TABLE `domain_treatment_spec` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `repeat_create` bit(1) DEFAULT NULL,
  `should_create_interval_hour` int(11) DEFAULT NULL,
  `resp_role_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9qcnwe2jngr264gaeuaijrlbo` (`resp_role_id`),
  CONSTRAINT `FK9qcnwe2jngr264gaeuaijrlbo` FOREIGN KEY (`resp_role_id`) REFERENCES `domain_organization_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_treatment_spec_state` */

CREATE TABLE `domain_treatment_spec_state` (
  `spec_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  KEY `FKlns0vrri348ca222c245ugiw0` (`spec_id`),
  CONSTRAINT `FKlns0vrri348ca222c245ugiw0` FOREIGN KEY (`spec_id`) REFERENCES `domain_treatment_spec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_user_role` */

CREATE TABLE `domain_user_role` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `role_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `user_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpto528e7kv8ukq1w051pfgmxi` (`role_id`),
  KEY `FKmhddh0yvx4vkqo0l0j6vs4ohy` (`user_id`),
  CONSTRAINT `FKmhddh0yvx4vkqo0l0j6vs4ohy` FOREIGN KEY (`user_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKpto528e7kv8ukq1w051pfgmxi` FOREIGN KEY (`role_id`) REFERENCES `domain_organization_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_visit` */

CREATE TABLE `domain_visit` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `area_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `bed` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `card_number` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `into_ward_date` datetime(6) DEFAULT NULL,
  `last` bit(1) DEFAULT NULL,
  `leave_ward_date` datetime(6) DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_leave_ward_date` datetime(6) DEFAULT NULL,
  `previous_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `doctor_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nurse_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state_desc` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `voucher_date` datetime(6) DEFAULT NULL,
  `area_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `charge_bill_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `clip_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `patient_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `previous_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `doctor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `nurse_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX5jdtlphv1lnuwm1o3rdctofj6` (`card_number`),
  KEY `FK5vphta4l3kdbwsee0m8nrewhe` (`area_id`),
  KEY `FK5qlnpg6sja0c76twl5i7kcrx4` (`charge_bill_id`),
  KEY `FKhy9kbh80oj40yjsr8bhbdx7s9` (`dept_id`),
  KEY `FKgl6ynrs38n6u3o8rnege96u4u` (`clip_id`),
  KEY `FKhlhyke393tsetcmsewmyrd9ct` (`patient_id`),
  KEY `FKd1aqy3lu8lkhp4obmfeqkggee` (`previous_dept_id`),
  KEY `FKksfirq7cxsmt5g4lhi64jx163` (`doctor_id`),
  KEY `FKda4yikkwhc2by9s2ui10sh30c` (`nurse_id`),
  CONSTRAINT `FK5qlnpg6sja0c76twl5i7kcrx4` FOREIGN KEY (`charge_bill_id`) REFERENCES `domain_cost_charge_bill` (`id`),
  CONSTRAINT `FK5vphta4l3kdbwsee0m8nrewhe` FOREIGN KEY (`area_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKd1aqy3lu8lkhp4obmfeqkggee` FOREIGN KEY (`previous_dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKda4yikkwhc2by9s2ui10sh30c` FOREIGN KEY (`nurse_id`) REFERENCES `domain_organization_user` (`id`),
  CONSTRAINT `FKgl6ynrs38n6u3o8rnege96u4u` FOREIGN KEY (`clip_id`) REFERENCES `domain_medical_record_clip` (`id`),
  CONSTRAINT `FKhlhyke393tsetcmsewmyrd9ct` FOREIGN KEY (`patient_id`) REFERENCES `domain_patient` (`id`),
  CONSTRAINT `FKhy9kbh80oj40yjsr8bhbdx7s9` FOREIGN KEY (`dept_id`) REFERENCES `domain_organization_unit` (`id`),
  CONSTRAINT `FKksfirq7cxsmt5g4lhi64jx163` FOREIGN KEY (`doctor_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `domain_visit_log` */

CREATE TABLE `domain_visit_log` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `info` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `operator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `operator_id` varchar(36) COLLATE utf8_bin NOT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbv48g1dy2hporda8mvq3b0nnl` (`operator_id`),
  KEY `FK7t1buefu3yv1jhkxchsiwpaum` (`visit_id`),
  CONSTRAINT `FK7t1buefu3yv1jhkxchsiwpaum` FOREIGN KEY (`visit_id`) REFERENCES `domain_visit` (`id`),
  CONSTRAINT `FKbv48g1dy2hporda8mvq3b0nnl` FOREIGN KEY (`operator_id`) REFERENCES `domain_organization_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `his_app_recordroom_medical_case` */

CREATE TABLE `his_app_recordroom_medical_case` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_history_date` datetime(6) DEFAULT NULL,
  `case_number` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `clip_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `creator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `position` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `his_domain_visit` */

CREATE TABLE `his_domain_visit` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_history_date` datetime(6) DEFAULT NULL,
  `area_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `area_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `bed` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `card_number` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `charge_bill_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `into_ward_date` datetime(6) DEFAULT NULL,
  `last` bit(1) DEFAULT NULL,
  `leave_ward_date` datetime(6) DEFAULT NULL,
  `clip_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `patient_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `patient_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `plan_leave_ward_date` datetime(6) DEFAULT NULL,
  `previous_dept_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `previous_dept_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `doctor_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `doctor_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `nurse_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `nurse_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `state_desc` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `voucher_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXnanbmu576g7ip2dx5n8vprf5l` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `his_domain_visit_log` */

CREATE TABLE `his_domain_visit_log` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `create_history_date` datetime(6) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `info` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `operator_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `operator_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `visit_id` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `visit_name` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `portal_sys_date` */

CREATE TABLE `portal_sys_date` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `sys_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
