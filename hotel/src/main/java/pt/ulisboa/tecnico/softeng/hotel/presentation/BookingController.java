package pt.ulisboa.tecnico.softeng.hotel.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.services.local.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.BookingData;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData.CopyDepth;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.RoomData;

@Controller
@RequestMapping(value = "/hotels/{hotelCode}/rooms/{roomNumber}/bookings")
public class BookingController {
	private static Logger logger = LoggerFactory.getLogger(BookingController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showBookings(Model model, @PathVariable String hotelCode, String roomNumber) {
		logger.info("showBookings code:{} number: {}", hotelCode, roomNumber);

		RoomData roomData = HotelInterface.getRoomData(hotelCode, roomNumber, CopyDepth.BOOKING);

		if (roomData == null) {
			model.addAttribute("error", "Error: it does not exist a broker with the code " + roomNumber);
			model.addAttribute("room", new RoomData());
			model.addAttribute("rooms", HotelInterface.getRooms(hotelCode));
			return "rooms";
		} else {
			model.addAttribute("booking", new BookingData());
			model.addAttribute("room", roomData);
			return "bookings";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitBooking(Model model, @PathVariable String hotelCode, @PathVariable String roomNumber, @ModelAttribute BookingData bookingData) {
		logger.info("submitBooking roomNumber:{}, reference:{}, arrival:{}, departure:{}", roomNumber, bookingData.getReference(),
				bookingData.getArrival(), bookingData.getDeparture());

		try {
			HotelInterface.createBooking(hotelCode, roomNumber, bookingData);
		} catch (HotelException he) {
			model.addAttribute("error", "Error: it was not possible to create the room booking");
			model.addAttribute("booking", bookingData);
			model.addAttribute("room", HotelInterface.getRoomData(hotelCode, roomNumber, CopyDepth.BOOKING));
			return "bookings";
		}

		return "redirect:/hotels/{hotelCode}/rooms/" + roomNumber + "/bookings";
	}

}