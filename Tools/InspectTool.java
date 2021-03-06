package scripts.LANScriptTools.Tools;

import javax.swing.table.DefaultTableModel;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.LANScriptTools.Threading.ScriptToolsThread;
import scripts.LanAPI.Game.Helpers.NPCsHelper;
import scripts.LanAPI.Game.Helpers.ObjectsHelper;
import scripts.LanAPI.Game.Helpers.PlayersHelper;

/**
 * @author Laniax
 *
 */
public class InspectTool implements AbstractTool {

	private final ScriptToolsThread script;
	
	public InspectTool(ScriptToolsThread script) {
		this.script = script;
	}

	private String arrayToSingle(String[] array) {

		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < array.length; i++) {

			if (i == array.length-1)// last one
				builder.append(array[i]);
			else
				builder.append(array[i]+", ");

		}

		return builder.toString();
	}

	public void refresh(RSTile tile) {

		if (tile!= null) {
			
			DefaultTableModel model = (DefaultTableModel)script.dock.tableInspect.getModel();
			model.setNumRows(0);

			// Find all npcs on tile.
			RSNPC[] npcs = NPCsHelper.getAt(tile);
			for (RSNPC npc : npcs) {
				model.addRow(new Object[] {npc.getID(), "NPC", npc.getName(), arrayToSingle(npc.getActions()) });
			}

			// Find the object on tile.
			RSObject obj = ObjectsHelper.getAt(tile);
			if (obj != null) {
				RSObjectDefinition objDef = obj.getDefinition();
				// There are a bazillion 'null' objects ingame. So lets filter those out.
				if (objDef != null && !objDef.getName().equals("null")) {
					String name = objDef != null ? objDef.getName() : "";
					String actions = objDef != null ? arrayToSingle(objDef.getActions()) : "";
					model.addRow(new Object[] { obj.getID(), "Object", name, actions });
				}
			}

			// Find all grounditems on tile.
			RSGroundItem[] items = GroundItems.getAt(tile);
			for (RSGroundItem item : items) {
				RSItemDefinition itemDef = item.getDefinition();
				String name = itemDef != null ? itemDef.getName() : "";
				model.addRow(new Object[] {item.getID(), "Item", name, "Take" });
			}

			// And all other players
			RSPlayer[] players = PlayersHelper.findNear(tile);
			for (RSPlayer player : players) {
				model.addRow(new Object[] {null , "Player", player.getName(), "" });
			}
		}
	}

	@Override
	public void onTabChange() {
		// Nothing to do!
	}

	@Override
	public void onTileSelected(RSTile tile) {
		refresh(tile);
	}
}
