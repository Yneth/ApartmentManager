(function () {
    $(document).ready(function () {
        $('.input-daterange').each(function () {
            $(this).datepicker({
                format: 'yyyy-mm-dd',
                startDate: new Date(),
                beforeShowDay: function (date) {
                    return typeof dates === "undefined" ||
                        $.inArray(date, dates) === -1;
                }
            });
        });
    });
})();
