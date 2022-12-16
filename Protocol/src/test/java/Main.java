import ru.kpfu.itis.general.helpers.TypesHelper;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        byte erorByte = 1;
        //System.out.println(TypesHelper.ifThereSuchBytes(erorByte));

        Socket socket = new Socket();
        MessageOutputStream out = new MessageOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        Message message = new Message();

        byte[] bytes = new byte[]{0, 1, 2, 6, 6, 6};
        message.setType((byte) 2);
        message.setBody(bytes);

        out.writeMessage(message);

    }
}
