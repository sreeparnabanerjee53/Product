use product;

CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `currentPrice` double NOT NULL,
  `lastUpdated` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `product` (`id`, `createdAt`, `currentPrice`, `lastUpdated`, `name`)
VALUES
	('P_cf21c452-fd93-4483-a6ad-d9985943ea09', '2019-12-04 18:19:59', 9999, '2019-12-04 18:19:59', 'motoZ');
