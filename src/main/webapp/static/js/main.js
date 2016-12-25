(function () {
    $(document).ready(function () {
        $('.input-daterange').each(function () {
            $(this).datepicker({
                minDate: Date.now(),
                beforeShowDay: function (date) {
                    return $.inArray(date, dates) === -1;
                }
            });
        });
    });
})();
