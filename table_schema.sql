CREATE TABLE `Address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `details` longtext,
  `slots` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `vegetarian_opt` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `Project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isAdmin` bit(1) NOT NULL,
  `shortname` varchar(255) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `Booking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vegetarian` bit(1) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6713A039BAFEA2D6` (`event_id`),
  KEY `FK6713A03947140EFE` (`user_id`),
  CONSTRAINT `FK6713A03947140EFE` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK6713A039BAFEA2D6` FOREIGN KEY (`event_id`) REFERENCES `Event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `user_project` (
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
