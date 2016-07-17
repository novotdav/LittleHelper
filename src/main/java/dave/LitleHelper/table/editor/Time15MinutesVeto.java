package dave.LitleHelper.table.editor;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;

public class Time15MinutesVeto implements TimeVetoPolicy {

	@Override
	public boolean isTimeAllowed(LocalTime time) {
		return time.get(ChronoField.MINUTE_OF_HOUR) % 15 == 0;
	}

}
