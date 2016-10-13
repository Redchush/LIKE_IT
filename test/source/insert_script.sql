-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`categoryTag`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`categoryTag` (`id`, `title`, `created_date`, `description`, `parent_category`, `published`) VALUES (1, 'java наше все', DEFAULT, 'паблик о java', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`categoryTag` (`id`, `title`, `created_date`, `description`, `parent_category`, `published`) VALUES (2, 'sql спасет мир', DEFAULT, 'паблик о sql ', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`categoryTag` (`id`, `title`, `created_date`, `description`, `parent_category`, `published`) VALUES (3, 'frontend ', DEFAULT, 'изучаем вместе html и css', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`categoryTag` (`id`, `title`, `created_date`, `description`, `parent_category`, `published`) VALUES (4, 'html ', DEFAULT, NULL, 3, DEFAULT);

COMMIT;

-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`favoriteUserTag`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`favoriteUserTag` (`id`, `role_id`, `login`, `password`, `email`, `last_name`, `first_name`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 'lara', 'm23kujj', 'lara@mail.ru', NULL, NULL, '2016-06-14 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`favoriteUserTag` (`id`, `role_id`, `login`, `password`, `email`, `last_name`, `first_name`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 'kennni', 'nskofkjal', 'kenni@yahoo.com', NULL, NULL, '2016-03-15 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`favoriteUserTag` (`id`, `role_id`, `login`, `password`, `email`, `last_name`, `first_name`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 'winni', 'aljdsoifjoi3', 'winni@mail.ru', NULL, NULL, '2016-01-16 14:33:04', NULL, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`postTag`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`postTag` (`id`, `user_id`, `category_id`, `title`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 1, 1, 'Публичный API для получения сегодняшней даты', 'Есть ли сервис, который предоставляет сегодняшнюю дату с API, да еще и безвозмездно?', '2016-08-14 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`postTag` (`id`, `user_id`, `category_id`, `title`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 1, 'путь до папки Downloads', 'Друзья, есть такой момент. Мне нужно узнать путь до папки Downloads, если есть флешка то флешки, если нет то памяти телефона.', '2016-09-15 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`postTag` (`id`, `user_id`, `category_id`, `title`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 2, 'Mysql Помогите оптимизировать запрос', '$mysql_query  = \"SELECT \". \"(SELECT SUM(sum)FROM transactions WHERE oper=1 AND user=$id) AS game, \" .\"(SELECT SUM(sum)FROM transactions WHERE oper=4 AND user=$id) AS discount, \". \"(SELECT SUM(sum)FROM transactions WHERE oper=3 AND user=$id) AS salery, \"', '2016-01-16 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`postTag` (`id`, `user_id`, `category_id`, `title`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 4, 'Как рисовать полупрозрачный лини на Canvas без наложения цвета?', 'Всем привет у меня тут образовалась проблема.Я делаю небольшую рисовалку и по заданию нужно чтобы можно было на канве рисовать линии с разным уровнем прозрачности. Вот тут пример такой рисовалки: https://jsfiddle.net/08cw6mh7/', '2016-05-16 14:33:04', NULL, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`answers`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`answers` (`id`, `user_id`, `post_id`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 4, 'пример конвертилки из цвета с прозрачностью в цвет без прозрачности с учетом фона: yolijn.com/convert-rgba-to-rgb ', '2016-02-16 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`answers` (`id`, `user_id`, `post_id`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 1, 3, 'SELECT user, user_name,        SUM(if(oper=1,sum,0)) as game,        SUM(if(oper=4,sum,0)) as discount,        SUM(if(oper=3,sum,0)) as salery,        SUM(if(oper=2 or oper=12,sum,0)) as costs   FROM transactions  WHERE oper in(1,2,3,4,12)  GROUP BY user, user_name', '2016-04-16 14:33:04', NULL, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`rating`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`rating` (`id`, `user_id`, `answer_id`, `rating`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 1, 5, '2016-05-16 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`rating` (`id`, `user_id`, `answer_id`, `rating`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 2, 4, '2016-06-11 14:33:04', NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`rating` (`id`, `user_id`, `answer_id`, `rating`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 2, 3, '2016-06-12 14:33:04', NULL, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`tags`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (1, 'java');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (2, 'sql');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (3, 'mysql');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (4, 'php');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (5, 'javascript');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (6, 'html');
INSERT INTO `LIKE_IT`.`tags` (`id`, `name`) VALUES (7, 'css');

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`favorite_user_tags`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (1, 1);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (1, 2);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (1, 3);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (2, 1);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (2, 4);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (2, 6);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (3, 5);
INSERT INTO `LIKE_IT`.`favorite_user_tags` (`user_id`, `tags_id`) VALUES (3, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`posts_tags`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`posts_tags` (`post_id`, `tag_id`) VALUES (1, 1);
INSERT INTO `LIKE_IT`.`posts_tags` (`post_id`, `tag_id`) VALUES (2, 1);
INSERT INTO `LIKE_IT`.`posts_tags` (`post_id`, `tag_id`) VALUES (3, 2);
INSERT INTO `LIKE_IT`.`posts_tags` (`post_id`, `tag_id`) VALUES (3, 3);
INSERT INTO `LIKE_IT`.`posts_tags` (`post_id`, `tag_id`) VALUES (4, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`comments`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`comments` (`id`, `user_id`, `answers_id`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 1, 'Спасибо, отличная ссылка', DEFAULT, NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`comments` (`id`, `user_id`, `answers_id`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 3, 2, 'Помогло', DEFAULT, NULL, DEFAULT);
INSERT INTO `LIKE_IT`.`comments` (`id`, `user_id`, `answers_id`, `content`, `created_date`, `updated_date`, `banned`) VALUES (DEFAULT, 2, 2, 'Отлично!', DEFAULT, NULL, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`categories_tags`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (1, 1);
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (2, 2);
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (3, 5);
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (3, 6);
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (3, 7);
INSERT INTO `LIKE_IT`.`categories_tags` (`category_id`, `tag_id`) VALUES (4, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`favorite_users_posts`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`favorite_users_posts` (`id`, `user_id`, `post_id`, `comment`) VALUES (DEFAULT, 1, 1, NULL);
INSERT INTO `LIKE_IT`.`favorite_users_posts` (`id`, `user_id`, `post_id`, `comment`) VALUES (DEFAULT, 1, 2, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`readed_posts`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (1, 1);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (1, 3);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (2, 2);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (2, 3);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (2, 4);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (3, 3);
INSERT INTO `LIKE_IT`.`readed_posts` (`user_id`, `post_id`) VALUES (3, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `LIKE_IT`.`rating_comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `LIKE_IT`;
INSERT INTO `LIKE_IT`.`rating_comment` (`id`, `rating_id`, `type`, `comment`, `banned`) VALUES (DEFAULT, 1, 1, 'done', DEFAULT);
INSERT INTO `LIKE_IT`.`rating_comment` (`id`, `rating_id`, `type`, `comment`, `banned`) VALUES (DEFAULT, 3, 1, 'norm', DEFAULT);

COMMIT;

