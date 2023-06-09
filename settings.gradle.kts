rootProject.name = "payments-lab"

include(":app")
include(":common")

/** account **/
include(
    ":account-api",
    ":account-api:account-application",
    ":account-api:account-domain",
    ":account-api:account-infrastructure",
    ":account-api:account-presentation"
)

/** payment **/
include(
    ":payment-api",
    ":payment-api:payment-application",
    ":payment-api:payment-domain",
    ":payment-api:payment-infrastructure",
    ":payment-api:payment-presentation",
)
