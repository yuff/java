insert into BOOKS (ID, TITLE, DESCRIPTION, AUTHOR, STATE) values (1, 'Java 8 in Action', '', 'Alan Mycroft', 'unavailable' );
insert into BOOKS (ID, TITLE, DESCRIPTION, AUTHOR, STATE) values (2, 'Spring 8 in Action', '', '', 'available' );

insert into USERS (ID, NAME, FIRST_NAME,LAST_NAME,DISPLAY_NAME,EMAIL) values(1,'I336048','Feifei', 'Yu', 'Yu Feifei','feifei.yu@sap.com');

insert into BORROWS (ID, BOOK_ID,USER_ID, STATE) values(S_BORROW.NEXTVAL,1,1, 'borrow');
