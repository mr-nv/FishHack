package de.roth.json.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {

	// Hier schreibst du deine Attribute hin
	public boolean autodisconnect_toggled = false;
	public boolean autodisconnect_autorespawn = false;
	public long autodisconnect_delay = 0;
	public long autodupe_disconnectdelay = 0;
	public long autodupe_logindelay = 0;
	public String autodupe_password = "";
	public String blockdirection = "NONE";
	public boolean boatfly_toggled = true;
	public double boatfly_upspeed = 0;
	public double boatfly_downspeed = 0;
	public boolean customchat_toggled = false;
	
	public Config() {
		// Hier die Standardwerte falls das jeweiligen Attribut nicht in der
		// config.json enthalten ist.
		autodisconnect_toggled = true;
		autodisconnect_autorespawn = false;
		autodisconnect_delay = 0;
		autodupe_disconnectdelay = 750;
		autodupe_logindelay = 800;
		autodupe_password = "";
		blockdirection = "NONE";
		boatfly_toggled = true;
		boatfly_upspeed = 2;
		boatfly_downspeed = 0;
		customchat_toggled = false;
	}

	// DON'T TOUCH THE FOLLOWING CODE
	private static Config instance;

	public static Config getInstance() {
		if (instance == null) {
			instance = fromDefaults();
		}
		return instance;
	}

	public static void load(File file) {
		instance = fromFile(file);

		// no config file found
		if (instance == null) {
			instance = fromDefaults();
		}
	}

	public static void load(String file) {
		load(new File(file));
	}

	private static Config fromDefaults() {
		Config config = new Config();
		return config;
	}

	public void toFile(String file) {
		toFile(new File(file));
	}

	public void toFile(File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonConfig = gson.toJson(this);
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(jsonConfig);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Config fromFile(File configFile) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			return gson.fromJson(reader, Config.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}