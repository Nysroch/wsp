package wsp.projekt.wsp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import wsp.projekt.wsp.logic.StudentLogic;
import wsp.projekt.wsp.model.Student;

// This service will be useful to us later, so first implement the
// UserDetailsService contract and let's implement its methods.

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
// Remember I said that we would need an 
// application user linked to the database
    private StudentLogic iStudentLogic;

// The default method permit us to search the user by username in the database
    @Override
    public UserDetails loadUserByUsername(String email) {
    	Student student = new Student();
		try {
			student = iStudentLogic.findStudentByEmail(email.toLowerCase());
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

        return  student;
    }
}