INSERT INTO seller (id, name) VALUES (1,'Marko Markovic');

INSERT INTO payment_type (id, name) VALUES (1,'paypal');
INSERT INTO payment_type (id, name) VALUES (2,'bank');
INSERT INTO payment_type (id, name) VALUES (3,'bitcoin');

INSERT INTO seller_payment_type (seller_id, payment_type_id) VALUES (1, 1);
INSERT INTO seller_payment_type (seller_id, payment_type_id) VALUES (1, 2);
INSERT INTO seller_payment_type (seller_id, payment_type_id) VALUES (1, 3);

