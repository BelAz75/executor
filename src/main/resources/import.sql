insert into authority(uuid, authority) values ('118414cd-1980-4776-9e95-88fbc10c440c', 'ROLE_USER')
insert into authority(uuid, authority) values ('d59a8ea4-c135-4b30-a475-e25772c68217', 'ROLE_TEACHER')
insert into authority(uuid, authority) values ('46badf7b-0560-4cc8-b55a-abc2f3d8f3ac', 'ROLE_ADMIN')

insert into users values ('664370bc-db14-47f3-ae37-0d61af534631', true, false, false, false, 'petr@gmail.com', 'Петр', 'Петров', '12345', 'petr');
insert into users values ('c4f25cf6-b394-4017-8072-60002bd5da87', true, false, false, false, 'vova@gmail.com', 'Владимир', 'Владимиров', '12345', 'vova');
insert into users values ('432d82af-0b0c-4c1b-bcd3-448a6a2f7452', true, false, false, false, 'hova@gmail.com', 'Илья', 'Ахметов', '12345', 'hova');
insert into users values ('05d0a9ce-7ad4-4a44-bc72-b35a3f17003c', true, false, false, false, 'lova@gmail.com', 'Ольга', 'Рахманинова', '12345', 'lova');
insert into users values ('3412d5f4-be9e-4fda-8dcf-f12f22b61304', true, false, false, false, 'maria.ivanova@gmail.com', 'Мария', 'Иванова', '12345', 'maria');

insert into user_authority(user_uuid, authority_uuid) values ('664370bc-db14-47f3-ae37-0d61af534631', '118414cd-1980-4776-9e95-88fbc10c440c');
insert into user_authority(user_uuid, authority_uuid) values ('c4f25cf6-b394-4017-8072-60002bd5da87', '118414cd-1980-4776-9e95-88fbc10c440c');
insert into user_authority(user_uuid, authority_uuid) values ('432d82af-0b0c-4c1b-bcd3-448a6a2f7452', '118414cd-1980-4776-9e95-88fbc10c440c');
insert into user_authority(user_uuid, authority_uuid) values ('05d0a9ce-7ad4-4a44-bc72-b35a3f17003c', '118414cd-1980-4776-9e95-88fbc10c440c');
insert into user_authority(user_uuid, authority_uuid) values ('3412d5f4-be9e-4fda-8dcf-f12f22b61304', 'd59a8ea4-c135-4b30-a475-e25772c68217');


