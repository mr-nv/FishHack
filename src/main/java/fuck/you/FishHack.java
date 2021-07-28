package fuck.you;

import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.io.IOException;

import com.google.common.eventbus.EventBus;

import fuck.you.loggers.Thunder;
import fuck.you.loggers.Wither;
import fuck.you.loggers.EndPortal;
import fuck.you.loggers.Apocalypse;

import fuck.you.helpers.AutoDisconnect;
import fuck.you.helpers.AutoDupe;
import fuck.you.helpers.BlockDirection;
import fuck.you.helpers.BoatFly;
import fuck.you.helpers.ConfigHelper;
import fuck.you.misc.ChangeName;
import fuck.you.misc.CustomChat;
import fuck.you.misc.Tickrate;
import fuck.you.misc.XrayBypass;
import de.roth.json.config.Config;

public class FishHack implements ModInitializer {
	
	public static EventBus eventBus;
	public static Thunder loggerThunder;
	public static EndPortal loggerEndPortal;
	public static Wither loggerWither;
	public static Apocalypse loggerApocalypse;
	
	public static BlockDirection helperBlockDirection;
	public static BoatFly helperBoatFly;
	public static ConfigHelper helperConfig;
	public static AutoDisconnect helperAutoDisconnect;
	public static AutoDupe helperAutoDupe;
	
	public static Tickrate miscTickrate;
	public static ChangeName miscChangeName;
	public static CustomChat miscCustomChat;
	public static XrayBypass miscXRayBypass;
	
	@Override
	public void onInitialize( ) {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//Config.load( "fishhack.json" );
		
		eventBus = new EventBus( );
		loggerThunder = new Thunder( );
		loggerEndPortal = new EndPortal( );
		loggerWither = new Wither( );
		loggerApocalypse = new Apocalypse( );
		
		helperBlockDirection = new BlockDirection( );
		helperBoatFly = new BoatFly( );
		helperConfig = new ConfigHelper( );
		helperAutoDisconnect = new AutoDisconnect( );
		helperAutoDupe = new AutoDupe( );
		
		miscTickrate = new Tickrate( );
		miscChangeName = new ChangeName( );
		miscCustomChat = new CustomChat( );
		miscXRayBypass = new XrayBypass( );
		
		File cfgfile = new File( "fishhack.json" );
		
		Config.load( "fishhack.json" );
		
		if( !cfgfile.exists( ) )
			Config.getInstance( ).toFile( "fishhack.json" );
		
		helperConfig.loadConfig( );
		
		eventBus.register( loggerThunder );
		eventBus.register( loggerEndPortal );
		eventBus.register( loggerWither );
		eventBus.register( loggerApocalypse );
		
		eventBus.register( helperBlockDirection );
		eventBus.register( helperBoatFly );
		eventBus.register( helperConfig );
		eventBus.register( helperAutoDisconnect );
		eventBus.register( helperAutoDupe );
		
		//eventBus.register( miscTickrate );
		eventBus.register( miscChangeName );
		eventBus.register( miscCustomChat );
		eventBus.register( miscXRayBypass );
		
		helperConfig.createThread( );
		
		System.out.println( "[FishHack] Initialized" );
	}
}
