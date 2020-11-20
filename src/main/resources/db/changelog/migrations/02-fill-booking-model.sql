
--select 'drop table if exists "' || tablename || '" cascade;'
--from pg_tables
--where schemaname = 'public';

INSERT INTO app_user(id,first_name, last_name, email, birth_date, phone_number)
VALUES
(1, 'Alex', 'Shinkevich', 'alexshinkevich@mail.ru', '1980-08-13', '123'),
(2, 'Alex1', 'Shinkevich1', 'alexshinkevich1@mail.ru', '1981-08-13', '321'),
(3, 'Alex2', 'Shinkevich2', 'alexshinkevich2@mail.ru', '1981-08-13', '589');

INSERT INTO auditorium(id, name, number_of_seats)
	VALUES
	(1, 'auditorium1', '15'),
	(2, 'auditorium2', '20');

INSERT INTO event(id, name, base_price, rating, ticket_price)
	VALUES
	(1, 'Шекспир', 15.5, 'MID', 16),
	(2, '1+1', 17, 'HIGH', 22),
	(3, 'Inception', 23, 'HIGH', 26.6);
	
INSERT INTO movie_session(id, air_date, auditorium_id, event_id)
	VALUES
	(1, to_timestamp('18-09-2019 10:00:00', 'dd-mm-yyyy hh24:mi:ss'), 1, 1),
	(2, to_timestamp('19-09-2019 15:00:00', 'dd-mm-yyyy hh24:mi:ss'), 1, 2),
	(3, to_timestamp('20-09-2019 16:00:00', 'dd-mm-yyyy hh24:mi:ss'), 1, 3);

INSERT INTO seat(id, seat_number, seat_type, is_available, auditorium_id)
	VALUES
	(1, 1, 'ORDINARY', true, 1),
	(2, 2, 'ORDINARY', true, 1),
	(3, 3, 'ORDINARY', true, 1)
	;

INSERT INTO ticket(id, date_time, user_id, event_id, seat_id)
	VALUES
	(1,  now() + interval '1' day, 1, 1, 1),
	(2,  now() + interval '1' day, 2, 1, 2);

INSERT into user_account(id, balance, user_id)
	VALUES
    (1, 158.54, 1);

SELECT setval('auditorium_id_seq', max(id))
FROM   auditorium;

SELECT setval('event_id_seq', max(id))
FROM   event;

SELECT setval('movie_session_id_seq', max(id))
FROM   movie_session;

SELECT setval('seat_id_seq', max(id))
FROM   seat;

SELECT setval('ticket_id_seq', max(id))
FROM   ticket;

SELECT setval('app_user_id_seq', max(id))
FROM   app_user;

SELECT setval('user_account_id_seq', max(id))
FROM   user_account;
