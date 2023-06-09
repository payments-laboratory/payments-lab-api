package org.collaborators.paymentslab.account.domain.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class DuplicatedEmailException : ServiceException(ErrorCode.DUPLICATE_EMAIL)