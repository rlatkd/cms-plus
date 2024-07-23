package kr.or.kosa.cmsplusmain.domain.billing.entity;

import kr.or.kosa.cmsplusmain.domain.billing.exception.InvalidBillingStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BillingState {
	private final Field field;
	private final boolean isEnabled;
	private final String reason;

	public BillingState(Field field, Boolean isEnabled) {
		this.field = field;
		this.isEnabled = isEnabled;
		this.reason = null;
	}

	public enum Field {
		UPDATE {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canBeUpdated();
			}
		},
		DELETE {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canBeDeleted();
			}
		},
		SEND_INVOICE {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canSendInvoice();
			}
		},
		CANCEL_INVOICE {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canCancelInvoice();
			}
		},
		PAY_REALTIME {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canPayRealtime();
			}
		},
		CANCEL_PAYMENT {
			@Override
			public BillingState checkState(Billing billing) {
				return billing.canCancelPaid();
			}
		};

		public abstract BillingState checkState(Billing billing);

		public void validateState(Billing billing) {
			BillingState state = checkState(billing);
			if (!state.isEnabled()) {
				throw new InvalidBillingStatusException(state);
			}
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}
}
