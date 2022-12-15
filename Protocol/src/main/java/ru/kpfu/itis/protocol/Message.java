package ru.kpfu.itis.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private byte type;
    private byte[] body;

    public Message(byte type) {
        this.type = type;
        body = new byte[0];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < body.length; i = i + 2) {
            char a = (char) (body[i] << 8 | body[i + 1]);
            sb.append(a);
        }

        return sb.toString();
    }
}
