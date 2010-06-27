/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author alfred
 */
public class MyYaml {
    private static final Yaml YAML = setup();

    /**
     * Yaml will pretty-print.
     * @return
     */
    public static Yaml setup() {
        if (YAML == null) {
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml1 = new Yaml(dumperOptions);
            return yaml1;
        }
        return YAML;
    }

    public static Yaml getYaml() {
        return YAML;
    }
}
