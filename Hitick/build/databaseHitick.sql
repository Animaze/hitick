/*Hitick Database */
/*Database password on jelastics :CIThvl84492*/
SET FOREIGN_KEY_CHECKS=0;
create database hitick;
use hitick;
/* group_admin_map */
CREATE TABLE `group_admin_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref_admin_id` int(11) NOT NULL,
  `ref_group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `ref_admin_id_idx` (`ref_admin_id`),
  KEY `ref_group_id_idx` (`ref_group_id`),
  CONSTRAINT `fk_group_admin_constraint_admin_id` FOREIGN KEY (`ref_admin_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_admin_constraint_group_id` FOREIGN KEY (`ref_group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/* group_member_map */
CREATE TABLE `group_member_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref_group_id` int(11) NOT NULL,
  `ref_member_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contraint_group_member_map_member_id_idx` (`ref_member_id`),
  KEY `fk_contraint_group_member_map_group_id_idx` (`ref_group_id`),
  CONSTRAINT `fk_contraint_group_member_map_group_id` FOREIGN KEY (`ref_group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contraint_group_member_map_member_id` FOREIGN KEY (`ref_member_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/* groups */
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_count` int(11) NOT NULL,
  `ref_admin_id` int(11) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `group_password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `ref_user_id_idx` (`member_count`),
  KEY `ref_admin_id_idx` (`ref_admin_id`),
  CONSTRAINT `fk_group_users_constraint_admin_id` FOREIGN KEY (`ref_admin_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/* questions */
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref_group_id` int(11) NOT NULL,
  `question` varchar(1000) NOT NULL,
  `yes_count` int(11) NOT NULL,
  `no_count` int(11) NOT NULL,
  `did_not_vote` int(11) NOT NULL,
  `result` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_group_question_constraint_group_id_idx` (`ref_group_id`),
  CONSTRAINT `fk_group_question_constraint_group_id` FOREIGN KEY (`ref_group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/* users */
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
   `gcm_registration_id` longtext,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  `institution` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/* voting_data */
CREATE TABLE `voting_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref_member_id` int(11) NOT NULL,
  `ref_group_id` int(11) NOT NULL,
  `ref_question_id` int(11) NOT NULL,
  `vote` int(11) NOT NULL,
  `time_of_posting_question` varchar(45) DEFAULT NULL,
  `stipulated_time_for_voting` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_voting_user_constraint_member_id_idx` (`ref_member_id`),
  KEY `fk_voting_group_constraint_group_id_idx` (`ref_group_id`),
  KEY `fk_voting_question_constraint_question_id_idx` (`ref_question_id`),
  CONSTRAINT `fk_voting_group_constraint_group_id` FOREIGN KEY (`ref_group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_voting_question_constraint_question_id` FOREIGN KEY (`ref_question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_voting_user_constraint_member_id` FOREIGN KEY (`ref_member_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
