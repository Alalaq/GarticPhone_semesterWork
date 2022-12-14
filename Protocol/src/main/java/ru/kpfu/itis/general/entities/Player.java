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
    protected Boolean status; //whether this player already voted or not
    protected Boolean readiness; //whether this player ready or not
    protected Boolean isAdmin;
    protected Drawing image; //the image that came to the player (or that player has drawn???)
    protected Player vote; // id of person who voted for this player
    protected transient Room room;

    //todo: handle case with null room
    public void exitRoom() {
        if (room != null) {
            room.deletePlayer(this);
            room = null;
        }
    }

    public boolean inRoom() {
        if (room == null) {
            return false;
        }
        return true;
    }
}
