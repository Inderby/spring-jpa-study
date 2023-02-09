insert into publisher(`id`, `name`) values (1, 'inderby');
insert into book (`id`, `name`, `publisher_id`, `deleted`, `status`) values (1, 'JPA study', 1, false, 100);
insert into book (`id`, `name`, `publisher_id`, `deleted`, `status`) values (2, 'JPA study2', 1, false, 200);
insert into book (`id`, `name`, `publisher_id`, `deleted`, `status`) values (3, 'JPA study3', 1, true, 100);

insert into review(`id`, `title`, `score`, `user_id`, `book_id`) values (1, '내 인생을 바꾼 책', '너무너무 좋았어요', 5.0, 1, 1);
insert into review(`id`, `title`, `score`, `user_id`, `book_id`) values (2, '너무 진도가 빨라요', '조금 별로였어요', 3.0, 2, 2);

insert into comment(`id`, `comment`, `review_id`) values (1, '저도 좋았어요', 1);

insert into comment(`id`, `comment`, `review_id`)values (2, '저는 별로 였는데요', 1);

insert into comment(`id`, `comment`, `review_id`)values (3, '저도 그냥 그랬어요', 2);
