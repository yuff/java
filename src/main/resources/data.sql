insert into BOOKS (ID, TITLE, DESCRIPTION, AUTHOR, STATE) values (S_BOOK.NEXTVAL, 'Java 8 in Action', '', 'Alan Mycroft', 'unavailable' );
insert into BOOKS (ID, TITLE, DESCRIPTION, AUTHOR, STATE) values (S_BOOK.NEXTVAL, 'Spring 8 in Action', '', '', 'available' );

insert into USERS (ID, NAME, FIRST_NAME,LAST_NAME,DISPLAY_NAME,EMAIL) values(S_USER.NEXTVAL,'I336048','Feifei', 'Yu', 'Yu Feifei','feifei.yu@sap.com');

insert into BORROWS (ID, BOOK_ID,USER_ID, STATE, START_DATE) values(S_BORROW.NEXTVAL,1,1, 'borrow', CURRENT_TIMESTAMP);
