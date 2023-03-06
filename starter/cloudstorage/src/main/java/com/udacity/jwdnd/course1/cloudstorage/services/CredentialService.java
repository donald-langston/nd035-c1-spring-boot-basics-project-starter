package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private EncryptionService encryptionService;

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer addCredential(Credential credential) { return credentialMapper.addCredential(credential); }

    public boolean editCredential(Credential credential) { return credentialMapper.editCredential(credential); }

    public boolean deleteCredential(Integer credentialId) { return credentialMapper.deleteCredential(credentialId); }

    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public String decryptPassword(String password, String key) { return encryptionService.decryptValue(password, key); }

    public String getEncryptionKey(Credential credential) { return credentialMapper.getEncryptionKey(credential); }
}
