package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Status;

@Service
@Transactional
public class StatusService {

	public String getStatusToString(final Status status) {

		String result = "";
		switch (status) {
		case ACCEPTED:
			result = "ACCEPTED";
			break;
		case CANCELLED:
			result = "CANCELLED";
			break;
		case DUE:
			result = "DUE";
			break;
		case PENDING:
			result = "PENDING";
			break;
		case REJECTED:
			result = "REJECTED";
			break;
		default:
			result = "";
			break;

		}

		return result;
	}
}
