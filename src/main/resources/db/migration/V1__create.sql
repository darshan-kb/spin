CREATE SEQUENCE public.game_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.game (
	is_game_over bool NOT NULL,
	result_value int4 NOT NULL,
	game_id int8 NOT NULL,
	game_time_stamp timestamp(6) NULL,
	total_amount float8 NOT NULL,
	CONSTRAINT game_pkey PRIMARY KEY (game_id)
);

CREATE SEQUENCE public.ticket_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.ticket (
	total_amount float8 NOT NULL,
	game_game_id int8 NULL,
	ticket_id int8 NOT NULL,
	"timestamp" timestamp(6) NULL,
	external_id varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT ticket_pkey PRIMARY KEY (ticket_id)
);


ALTER TABLE public.ticket ADD CONSTRAINT fkjyeabadj7o50rtsvpfvb8ui4x FOREIGN KEY (game_game_id) REFERENCES public.game(game_id);

CREATE SEQUENCE public.bet_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.bet (
	amount int4 NOT NULL,
	value int4 NOT NULL,
	bet_id int8 NOT NULL,
	ticket_ticket_id int8 NULL,
	bet_name varchar(255) NULL,
	CONSTRAINT bet_pkey PRIMARY KEY (bet_id)
);


-- public.bet foreign keys

ALTER TABLE public.bet ADD CONSTRAINT fkeul57xdw1banp2cmler9ow7uv FOREIGN KEY (ticket_ticket_id) REFERENCES public.ticket(ticket_id);

CREATE SEQUENCE public.bet_category_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.bet_category (
	bet_payout float8 NOT NULL,
	id serial4 NOT NULL,
	bet_name varchar(255) NULL,
	CONSTRAINT bet_category_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.bet_values_map_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.bet_values_map (
	bet_index int4 NOT NULL,
	id bigserial NOT NULL,
	bet_name varchar(255) NULL,
	bet_values varchar(255) NULL,
	CONSTRAINT bet_values_map_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.claim_bet_claim_bet_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.claim_bet (
	amount float8 NOT NULL,
	claimed bool NOT NULL,
	bet_bet_id int8 NULL,
	claim_bet_id bigserial NOT NULL,
	game_game_id int8 NULL,
	ticket_ticket_id int8 NULL,
	username varchar(255) NULL,
	CONSTRAINT claim_bet_bet_bet_id_key UNIQUE (bet_bet_id),
	CONSTRAINT claim_bet_pkey PRIMARY KEY (claim_bet_id)
);


-- public.claim_bet foreign keys

ALTER TABLE public.claim_bet ADD CONSTRAINT fk1ftdk6l96n64nwsq5tlg4hwt4 FOREIGN KEY (game_game_id) REFERENCES public.game(game_id);
ALTER TABLE public.claim_bet ADD CONSTRAINT fknxewn0m3rdxtw8cbhryvyej2s FOREIGN KEY (bet_bet_id) REFERENCES public.bet(bet_id);
ALTER TABLE public.claim_bet ADD CONSTRAINT fktibyotm9tnodn0mu8d7evdkrp FOREIGN KEY (ticket_ticket_id) REFERENCES public.ticket(ticket_id);
