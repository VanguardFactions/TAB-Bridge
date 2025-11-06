package com.vanguardfactions.tab;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CmdTop;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FactionUtil {

  public static String getRelationTag(final @NotNull UUID viewer, final @NotNull UUID target) {
    final Faction faction = FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer(viewer)).getFaction();
    final Relation relation = faction.getRelationTo(FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer(target)));
    return getTag(faction, relation) + getPosition(faction);
  }

  private static String getTag(final Faction faction, final Relation relation) {
    if (faction.isWilderness()) {
      return " ";
    }
    final String tag = "[" + faction.getTag() + "] ";
    switch (relation) {
      case MEMBER:
        return ChatColor.GREEN + tag;
      case ALLY:
        return ChatColor.LIGHT_PURPLE + tag;
      case ENEMY:
        return ChatColor.RED + tag;
      default:
        return ChatColor.GRAY + tag;
    }
  }

  private static String getPosition(final Faction faction) {
    if (faction.isWilderness()) {
      return " ";
    }
    final int position = CmdTop.ORDERED_FACTIONS.indexOf(faction) + 1;
    final StringBuilder format = new StringBuilder();
    switch (position) {
      case 0:
        return format.append("&7").append("#?").toString();
      case 1:
        return format.append("&6").append("#").append(position).toString();
      case 2:
        return format.append("&#C0C0C0").append("#").append(position).toString();
      case 3:
        return format.append("&#CD8740").append("#").append(position).toString();
    }
    return format.append("&7").append("#").append(position).toString();
  }
}
