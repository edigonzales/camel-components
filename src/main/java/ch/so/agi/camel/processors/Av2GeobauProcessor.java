package ch.so.agi.camel.processors;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.interlis2.av2geobau.Av2geobau;

import ch.ehi.basics.logging.EhiLogger;
import ch.ehi.basics.settings.Settings;

public class Av2GeobauProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        File itfFile = exchange.getIn().getBody(File.class);
        Path tempDir = Files.createTempDirectory("av2geobau_");
        File dxfFile = Paths.get(tempDir.toFile().getAbsolutePath(), itfFile.getName().replaceFirst("[.][^.]+$", "") + ".dxf").toFile();        

        Settings settings=new Settings();
        settings.setValue(Av2geobau.SETTING_ILIDIRS, Av2geobau.SETTING_DEFAULT_ILIDIRS);

        EhiLogger.getInstance().setTraceFilter(false);
        
        boolean ok = Av2geobau.convert(itfFile, dxfFile, settings);
        
        if (!ok) {
            throw new Exception("could not convert: " + itfFile.getAbsolutePath());
        }
        
        exchange.getIn().setBody(dxfFile);
    }
}
