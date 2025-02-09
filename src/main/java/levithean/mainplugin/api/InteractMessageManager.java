package levithean.mainplugin.api;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class InteractMessageManager {

    private final TextComponent message_formate;

    public InteractMessageManager(String message) {
        message_formate = new TextComponent(ChatManager.setColor(message));
    }

    public void addMessage(String message) {
        message_formate.addExtra(ChatManager.setColor(message));
    }

    public void addMessage(InteractMessageManager message) {
        message_formate.addExtra(message.getMessage());
    }

    public void addCommand(String commande) {
        message_formate.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ChatManager.setColor("/" + commande)));
    }

    public void addHoverText(String texte) {
        message_formate.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatManager.setColor(texte))));
    }

    protected TextComponent getMessage() {
        return message_formate;
    }
}
