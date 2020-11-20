create table auditorium(
    id                bigserial  not null,
    name              text       not null,
    number_of_seats   int,
    constraint pk_auditorium_id primary key (id)
);

create table seat(
    id                bigserial  not null,
    seat_number       int        not null,
    seat_type         text,
    is_available      boolean,
    auditorium_id     bigint,
    constraint pk_seat_id            primary key (id),
    constraint fk_auditorium_id       foreign key (auditorium_id) references auditorium (id)
);

create table event(
    id                bigserial  not null,
    name              text       not null,
    base_price        numeric,
    rating            text,
    ticket_price      numeric,
    constraint pk_event_id primary key (id)
);

create table movie_session(
    id                bigserial  not null,
    air_date          timestamp,
    auditorium_id     bigint,
    event_id          bigint,
    constraint pk_movie_session_id primary key (id),
    constraint fk_auditorium_id    foreign key (auditorium_id) references auditorium (id),
    constraint fk_event_id         foreign key (event_id)      references event (id)
);

create table app_user(
    id                bigserial  not null,
    first_name        text,
    last_name         text,
    email             text,
    phone_number      text,
    birth_date        date,
    constraint pk_user_id primary key (id)
);

create table ticket(
    id                bigserial  not null,
    date_time         timestamp,
    user_id           bigint,
    event_id          bigint,
    seat_id           bigint,
    constraint pk_ticket_id primary key (id),
    constraint fk_user_id   foreign key (user_id)      references app_user (id),
    constraint fk_event_id  foreign key (event_id)     references event (id),
    constraint fk_seat_id   foreign key (seat_id)      references seat (id)
);

create table user_account(
    id                bigserial  not null,
    balance           numeric,
    user_id           bigint,
    constraint pk_user_account_id       primary key (id),
    constraint uk_user_account_user_id  unique (user_id),
    constraint fk_user_id               foreign key (user_id)      references app_user (id)
);

