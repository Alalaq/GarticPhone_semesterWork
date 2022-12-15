package ru.kpfu.itis.general.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    protected Long id;
    protected String nickname;
    protected Boolean status; //whether this player already made a move or not
    protected Boolean readiness; //whether this player ready or not (before game started)

    protected Drawing image; //the image that came to the player (or that player has drawn???)

    protected Integer money;
    protected transient Room room;

    //todo: handle case with null room
    public void exitRoom(){
        if (room != null) {
            room.deletePlayer(this);
            room = null;
        }
    }

    public boolean inRoom(){
        if (room == null){
            return false;
        }
        return true;
    }
}
