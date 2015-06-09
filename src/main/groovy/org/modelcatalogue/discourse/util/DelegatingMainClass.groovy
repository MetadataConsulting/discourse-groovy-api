package org.modelcatalogue.discourse.util

import org.modelcatalogue.discourse.api.Backups

class DelegatingMainClass {

    private static Map<String, Class> delegates = [backup: Backups]

    static void main(String... args) {
        if (!args) {
            println """
            Usage:
            discourse <command> [args]

            Available commands: ${delegates.keySet().sort().join(', ')}
            """.stripIndent()
            return
        }

        Class clazz = delegates[args[0]]

        if (!clazz) {
            println """
            No such command '${args[0]}'

            Available commands: ${delegates.keySet().sort().join(', ')}
            """.stripIndent()
            return
        }

        if (args.size() == 1) {
            clazz.main([] as String[])
        } else {
            clazz.main(args[1..-1] as String[])
        }

    }

}
