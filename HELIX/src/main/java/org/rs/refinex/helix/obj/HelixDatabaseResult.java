package org.rs.refinex.helix.obj;

import java.util.HashMap;

import org.rs.refinex.value.Referencable;

public class HelixDatabaseResult extends Referencable {
    public static class Row {
        public HashMap<String, Object> Column;

        public Row(HashMap<String, Object> Column) {
            this.Column = Column;
        }
    }
}
