import ru.kpfu.itis.listeners.GameListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            List<Long> list = new ArrayList<>();
            list.add(1L);
            list.add(2L);
            list.add(3L);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);

            out.writeObject(list);

            Message branchSent = new Message(Constants.SENDED_ONE_GAME_BRANCH, baos.toByteArray());

        } catch (IOException ignored) {
        }
    }
}