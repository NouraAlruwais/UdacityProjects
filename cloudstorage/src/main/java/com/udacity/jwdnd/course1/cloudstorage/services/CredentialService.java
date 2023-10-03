package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;
    public CredentialService(CredentialMapper credentialMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public int addCredential(Credential credential,String userName){
        int userId=userService.getUserid(userName);

        return credentialMapper.insert(new Credential(null,credential.getUrl(),credential.getUsername(),credential.getKey(), credential.getPassword(), userId));
    }
    public List<Credential> getCredential(int userId){
        return credentialMapper.getAllCredential(userId);
    }

    public void updateCredential(Credential credential){
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer id) {
        credentialMapper.delete(id);
    }

}
