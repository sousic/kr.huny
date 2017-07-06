package kr.huny.common.serializer;

import org.springframework.security.core.Authentication;

import java.io.*;

/**
 * Created by sousic on 2017-05-24.
 */
public class IAuthSerializerImpl implements IAuthSerializer {
    @Override
    public byte[] write(Authentication object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Authentication read(byte[] data) {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Authentication)objectInputStream.readObject();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
