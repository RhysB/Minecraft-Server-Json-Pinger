package com.johnymuffin.serverping;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class JsonBuilder {

	public static String jsonBuild() {
		// Build the JSON response.
		StringBuilder resp = new StringBuilder();
		resp.append("{");
		resp.append("\"serverName\":").append("\"" + com.johnymuffin.serverping.AtlisPing.GetServerName() +  "\"").append(",");
		resp.append("\"serverKey\":").append("\"" + com.johnymuffin.serverping.AtlisPing.GetServerKey() +  "\"").append(",");
		resp.append("\"externalIP\":").append("\"" + com.johnymuffin.serverping.AtlisPing.GetIP() +  "\"").append(",");
		resp.append("\"serverPort\":").append(Bukkit.getServer().getPort()).append(",");
		resp.append("\"playerCount\":").append(Bukkit.getServer().getOnlinePlayers().length).append(",");
		resp.append("\"maxPlayers\":").append(Bukkit.getServer().getMaxPlayers()).append(",");
		resp.append("\"playerList\":");
		resp.append("[");

		int count = 0;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			resp.append("\"" + player.getName() + "\"");
			if (++count < Bukkit.getServer().getOnlinePlayers().length) {
				resp.append(",");
			}
		}

		resp.append("],");
		
		
		//Player Cords
		resp.append("\"players\":");
		resp.append("[");
		count = 0;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			resp.append("{\"username\": \"" + player.getName() + "\", \"displayName\": \"" + player.getDisplayName() + "\",\"world\": \"" + player.getWorld().getName() + "\", \"x\": " + player.getLocation().getX() + ", \"y\": " + player.getLocation().getY() + ", \"Z\": " + player.getLocation().getBlockZ() + "}");
			if (++count < Bukkit.getServer().getOnlinePlayers().length) {
				resp.append(",");
			}
		}
		resp.append("],");
		
		
		//Server Worlds
		resp.append("\"worlds\":");
		resp.append("[");
		count = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			resp.append("{\"name\": \"" + world.getName() + "\"," + "\"gameTicks\":" + world.getTime() + "," + "\"worldTicks\":" + world.getFullTime() + ","+ "\"rain\":" + world.isThundering() + "}");
			if (++count < Bukkit.getServer().getWorlds().size()) {
				resp.append(",");
			}
		}
		resp.append("]");
		resp.append("}\n");
		return(resp.toString());
	}

}
