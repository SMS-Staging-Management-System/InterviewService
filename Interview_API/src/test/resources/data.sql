
/*Begin Insert Data*/
	/*feedback_status*/
		insert into feedback_status (feedback_status_description) values ('Pending');
		insert into feedback_status (feedback_status_description) values ('No Feedback');
		insert into feedback_status (feedback_status_description) values ('Selected for Second Round');
		insert into feedback_status (feedback_status_description) values ('Direct Hire');
		insert into feedback_status (feedback_status_description) values ('Selected');
	/*interview_format*/
		insert into interview_format (interview_format_description) values ('On Site');
		insert into interview_format (interview_format_description) values ('In Person');
		insert into interview_format (interview_format_description) values ('Video Call');
		insert into interview_format (interview_format_description) values ('Phone Call');
	/*client*/
		insert into client (client_name) values ('Dell');
		insert into client (client_name) values ('Hewlett Packard');
		insert into client (client_name) values ('Microsoft');
		insert into client (client_name) values ('Google');
		insert into client (client_name) values ('Amazon');
		insert into client (client_name) values ('Netflix');
	/*interview_feedback*/
		insert into interview_feedback (feedback_requested, feedback, feedback_received, feedback_delivered, feedback_status, interview_format)
			values ('2019-03-01 13:00:00', 'Solid interview.', '2019-03-02 14:00:00', '2019-03-03 15:00:00', 1, 1);
		insert into interview_feedback (feedback_requested, feedback, feedback_received, feedback_delivered, feedback_status, interview_format)
			values ('2019-03-02 13:00:00', 'Solid interview.', '2019-03-03 14:00:00', '2019-03-04 15:00:00', 2, 2);
		insert into interview_feedback (feedback_requested, feedback, feedback_received, feedback_delivered, feedback_status, interview_format)
			values ('2019-03-03 13:00:00', 'Solid interview.', '2019-03-04 14:00:00', '2019-03-05 15:00:00', 3, 3);
		insert into interview_feedback (feedback_requested, feedback, feedback_received, feedback_delivered, feedback_status, interview_format)
			values ('2019-03-04 13:00:00', 'Solid interview.', '2019-03-05 14:00:00', '2019-03-06 15:00:00', 4, 4);
		insert into interview_feedback (feedback_requested, feedback, feedback_received, feedback_delivered, feedback_status, interview_format)
			values ('2019-03-05 13:00:00', 'Solid interview.', '2019-03-06 14:00:00', '2019-03-07 15:00:00', 5, 1);
	/*associate_input*/
		insert into associate_input (received_notifications, description_provided, interview_format, proposed_format)
			values ('2019-02-28 14:00:00', true, 1, 1);
		insert into associate_input (received_notifications, description_provided, interview_format, proposed_format)
			values ('2019-02-27 12:15:00', true, 2, 2);
		insert into associate_input (received_notifications, description_provided, interview_format, proposed_format)
			values ('2019-02-27 12:00:00', true, 3, 3);
		insert into associate_input (received_notifications, description_provided, interview_format, proposed_format)
			values ('2019-03-04 12:00:00', true, 1, 4);
	/*Interview*/
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'abatson94@gmail.com', 'USF', '2019-02-28 12:00:00', '2019-02-28 14:00:00', '2019-03-01 16:00:00', 1, 1, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'kenneth.james.currie@gmail.com', 'USF', '2019-02-28 12:00:00', '2019-02-28 09:30:00', '2019-03-02 16:00:00', 2, 2, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'mohamedwomar21@gmail.com', 'Reston', '2019-02-28 12:00:00', '2019-02-27 14:00:00', '2019-03-03 16:00:00', 3, 3, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'goncalvesjohnp@gmail.com', 'Reston', '2019-02-28 12:00:00', '2019-02-28 14:00:00', '2019-03-04 16:00:00', 4, 4, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'agrav12825@gmail.com', 'USF', '2019-02-28 12:00:00', '2019-02-27 12:00:00', null, 5, null, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'loricodes@gmail.com', 'USF', '2019-02-28 12:00:00', '2019-02-28 14:00:00', null, null, null, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'hermes.lisoma@example.com', 'Reston', '2019-07-11 12:00:00', '2019-07-11 14:00:00', null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'david.ubah@example.com', 'Reston', '2019-07-18 12:00:00', null, null, null, null, 4);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'robert.simmons@example.com', 'Reston', '2019-07-06 12:00:00', '2019-07-06 14:00:00', '2019-07-06 16:00:00', null, null, 3);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'elias.friedman@example.com', 'Reston', '2019-07-05 12:00:00', '2019-07-05 14:00:00', '2019-07-05 16:00:00', null, null, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'michele.dexter@example.com', 'Reston', '2019-07-12 12:00:00', '2019-07-12 14:00:00', null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'tianzeng.liu@example.com', 'Reston', '2019-07-11 12:00:00', '2019-07-11 14:00:00', null, null, null, 3);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'eva.fann@example.com', 'Reston', '2019-07-19 12:00:00', null, null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'james.c@example.com', 'Reston', '2019-07-15 12:00:00', '2019-07-15 14:00:00', null, null, null, 4);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'ernesto.ballon@example.com', 'Reston', '2019-07-15 12:00:00', '2019-07-15 14:00:00', null, null, null, 3);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'ryan.walker@example.com', 'Reston', '2019-07-19 12:00:00', null, null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('abatson94@gmail.com', 'ralph.metellus@example.com', 'Reston', '2019-07-17 12:00:00', '2019-07-17 14:00:00', null, null, null, 4);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'gerald.horton@example.com', 'USF', '2019-07-06 12:00:00', '2019-07-06 14:00:00', '2019-07-06 16:00:00', null, null, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'penelope.winters@example.com', 'USF', '2019-07-13 12:00:00', '2019-07-13 14:00:00', null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'boris.carlson@example.com', 'USF', '2019-07-12 12:00:00', '2019-07-12 14:00:00', null, null, null, 4);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'quyen.lung@example.com', 'USF', '2019-07-05 12:00:00', '2019-07-05 14:00:00', '2019-07-05 16:00:00', null, null, 6);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'victor.santos@example.com', 'USF', '2019-07-03 12:00:00', '2019-07-03 14:00:00', '2019-07-03 16:00:00', null, null, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'rajesh.patel@example.com', 'USF', '2019-07-11 12:00:00', '2019-07-11 14:00:00', null, null, null, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'caleb.widogast@example.com', 'USF', '2019-07-11 12:00:00', '2019-07-11 14:00:00', null, null, null, 1);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'percival.merriwether@example.com', 'USF', '2019-07-08 12:00:00', '2019-07-08 14:00:00', '2019-07-08 16:00:00', null, null, 6);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'constance.markham@example.com', 'USF', '2019-07-16 12:00:00', '2019-07-16 14:00:00', null, null, null, 5);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'donald.anders@example.com', 'USF', '2019-07-03 12:00:00', '2019-07-03 14:00:00', '2019-07-03 16:00:00', null, null, 2);
		insert into interview (manager_email, associate_email, place, scheduled, notified, reviewed, interview_feedback, associate_input, client)
			values ('blake.kruppa@revature.com', 'valorie.wilson@example.com', 'USF', '2019-07-10 12:00:00', '2019-07-10 14:00:00', '2019-07-10 16:00:00', null, null, 3);
/*End Insert Data*/
