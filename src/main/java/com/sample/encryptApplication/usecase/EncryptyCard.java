package com.sample.encryptApplication.usecase;

import com.sample.encryptApplication.exception.EncryptException;
import com.sample.encryptApplication.gateway.ProxySecurityEncryptGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptyCard {

    private final ProxySecurityEncryptGateway proxySecurityCryptGateway;

    public String encrypt(String card) {
        return getEncrypt(card);
    }

    int i = 1;

    private String getEncrypt(String card) {
        while (Boolean.TRUE) {
            i++;
            try {
                Thread thread = new Thread("New Thread") {
                    public void run() {
                        String encrypted = proxySecurityCryptGateway.encrypt(card);
                        System.out.println(encrypted + " Encriptados" + i);
                    }
                };

                Thread threadDecrypt = new Thread("New Thread") {
                    public void run() {
                        String encrypted = proxySecurityCryptGateway.encrypt(card);
                        String decrypted = proxySecurityCryptGateway.decrypt(encrypted);
                        System.out.println(decrypted + " Decriptados " + i + "\n");
                    }
                };

                thread.run();
                threadDecrypt.run();
            } catch (final Exception e) {
                System.out.println("Error on encrypt value %s " + e.getMessage() + "\n" + e);
                throw new EncryptException();
            }

        }
           return null;

    }

}
