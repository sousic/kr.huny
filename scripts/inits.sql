INSERT INTO `kr.huny`.`authority` (`seq`, `authority_name`) VALUES (1, '방문자');
INSERT INTO `kr.huny`.`authority` (`seq`, `authority_name`) VALUES (10, '일반');
INSERT INTO `kr.huny`.`authority` (`seq`, `authority_name`) VALUES (100, '관리자');
INSERT INTO `kr.huny`.`authority` (`seq`, `authority_name`) VALUES (255, '슈퍼관리자');

INSERT INTO `kr.huny.site`.`user_authority` (`authority`, `user_no`) VALUES (10, 3);
INSERT INTO `kr.huny.site`.`user_authority` (`authority`, `user_no`) VALUES (100, 3);
INSERT INTO `kr.huny.site`.`user_authority` (`authority`, `user_no`) VALUES (255, 3);