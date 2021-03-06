package com.merobo.dtos;

import java.util.ArrayList;
import java.util.List;

public class MeetingRoomTo {

	private List<BookingTo> pinnacle = new ArrayList<BookingTo>();
	private List<BookingTo> other = new ArrayList<BookingTo>();

	public List<BookingTo> getPinnacle() {
		return pinnacle;
	}

	public List<BookingTo> getOther() {
		return other;
	}

	@Override
	public String toString() {
		return "MeetingRoomTo [pinnacle=" + pinnacle + ", other=" + other + "]";
	}

}
