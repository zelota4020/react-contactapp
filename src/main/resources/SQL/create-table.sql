-- contacts table

CREATE TABLE contacts (
	id uuid DEFAULT gen_random_uuid() NOT NULL,
	address varchar(255) NULL,
	email varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	phone varchar(255) NULL,
	photo_url varchar(255) NULL,
	status varchar(255) NULL,
	title varchar(255) NULL,
	CONSTRAINT contacts_pkey PRIMARY KEY (id)
);
